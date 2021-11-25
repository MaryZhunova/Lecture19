package com.example.recipe.presentation.switchfavourites.myrecipes

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.databinding.FragmentMyRecipesBinding
import com.example.recipe.presentation.models.MyRecipePresentationModel
import com.example.recipe.presentation.switchfavourites.myrecipes.viewmodel.MyRecipesViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Фрагмент для отображения списка своих рецептов
 */
class MyRecipesFragment : Fragment(), OnMyRecipeClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentMyRecipesBinding
    private lateinit var myRecipesViewModel: MyRecipesViewModel
    private lateinit var adapter: MyRecipesAdapter
    private lateinit var mCallback: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(recipePresentationModel: MyRecipePresentationModel)
        fun addRecipe()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as OnItemClickListener
        } catch (e: Exception) {
            throw Exception("$context should implement MyRecipesFragment.OnItemClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkApp.appComponent(requireContext()).inject(this)

        initRecyclerview()
        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        binding.addWord.setOnClickListener {
            mCallback.addRecipe()
        }
    }

    override fun onStart() {
        super.onStart()
        myRecipesViewModel.get()
    }

    private fun initRecyclerview() {
        adapter = MyRecipesAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun createViewModel() {
        myRecipesViewModel =
            ViewModelProvider(this, viewModelFactory)[MyRecipesViewModel::class.java]
    }

    private fun observeLiveData() {
        myRecipesViewModel.getProgressLiveData()
            .observe(viewLifecycleOwner) { isVisible: Boolean -> showProgress(isVisible) }
        myRecipesViewModel.getRecipesLiveData()
            .observe(viewLifecycleOwner) { recipes: List<MyRecipePresentationModel> ->
                showData(recipes.toMutableList())
            }
        myRecipesViewModel.getErrorLiveData()
            .observe(viewLifecycleOwner) { throwable: Throwable -> showError(throwable) }
    }

    private fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showData(recipes: MutableList<MyRecipePresentationModel>) {
        if (checkPermission()) {
            adapter.submitList(recipes)
        } else {
            requestPermission()
            adapter.submitList(recipes)
        }

    }

    private fun requestPermission() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                val uri = Uri.parse("package:com.example.recipe")
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        uri
                    )
                )
            }
            else -> {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    REQUEST_CODE
                )
            }
        }
    }

    private fun checkPermission() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> Environment.isExternalStorageManager()
        else -> checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showError(throwable: Throwable) {
        throwable.message?.let {
            Snackbar.make(binding.root, it, BaseTransientBottomBar.LENGTH_LONG).show()
        }
    }

    override fun onRecipeClick(recipePresentationModel: MyRecipePresentationModel) {
        mCallback.onClick(recipePresentationModel)
    }

    override fun onDeleteClick(recipePresentationModel: MyRecipePresentationModel) {
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
        myRecipesViewModel.deleteFromMyRecipes(recipePresentationModel)
    }

    companion object {
        const val REQUEST_CODE = 333
        fun newInstance() = MyRecipesFragment()
    }
}