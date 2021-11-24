package com.example.recipe.presentation.switchfavourites.myrecipes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.databinding.MyRecipesBinding
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.presentation.switchfavourites.myrecipes.viewmodel.MyRecipesViewModel
import com.example.recipe.presentation.switchfavourites.addrecipe.AddRecipeFragment
import javax.inject.Inject

/**
 * Фрагмент для отображения списка своих рецептов
 */
class MyRecipesFragment : Fragment(), OnMyRecipeClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: MyRecipesBinding
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
        binding = MyRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkApp.appComponent(requireContext()).inject(this)

        initRecycleview()
        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()
        //Отправить сетевой запрос с ключевым словом query через вью модель
        myRecipesViewModel.get()


        binding.addWord.setOnClickListener {
//            val viewGroup = FragmentContainerView(requireContext())
//            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
//            viewGroup.layoutParams = params
//            viewGroup.id = R.id.fragment_id
//            binding.root.addView(viewGroup)
//            requireActivity().supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_id, AddRecipeFragment.newInstance())
//                .addToBackStack("fragment")
//                .commit()
            mCallback.addRecipe()
        }
    }

    private fun initRecycleview() {
        adapter = MyRecipesAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        adapter.onItemClickListener = {
            mCallback.onClick(it)
        }
    }

    private fun createViewModel() {
        myRecipesViewModel = ViewModelProvider(this, viewModelFactory)[MyRecipesViewModel::class.java]
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
        adapter.updateList(recipes)
    }

    private fun showError(throwable: Throwable) {
        throwable.message?.let {
            binding.errorLayout.visibility = View.VISIBLE
            binding.errorText.text = it
        }
    }
    override fun onDeleteClick(recipePresentationModel: MyRecipePresentationModel) {
        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
        myRecipesViewModel.deleteFromMyRecipes(recipePresentationModel)
    }

    companion object {
        fun newInstance() = MyRecipesFragment()
    }
}