package com.example.recipe.presentation.switchfavourites.myrecipes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.databinding.FragmentMyRecipesBinding
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.switchfavourites.myrecipes.viewmodel.MyRecipesViewModel
import com.example.recipe.presentation.recipedetail.RecipeDetailActivity
import com.example.recipe.presentation.switchfavourites.addrecipe.AddRecipeFragment
import javax.inject.Inject

/**
 * Фрагмент для отображения списка своих рецептов
 */
class MyRecipesFragment : Fragment(), OnMyRecipeClickListener {
    private lateinit var binding: FragmentMyRecipesBinding
    private lateinit var myRecipesViewModel: MyRecipesViewModel
    private lateinit var adapter: MyRecipesAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = MyRecipesFragment()
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

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()
        //Отправить сетевой запрос с ключевым словом query через вью модель
        myRecipesViewModel.get()

        binding.result.setOnClickListener {
            val viewGroup = FragmentContainerView(requireContext())
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
            viewGroup.layoutParams = params
            viewGroup.id = R.id.fragment_id
            binding.root.addView(viewGroup)
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_id, AddRecipeFragment.newInstance())
                .addToBackStack("fragment")
                .commit()
        }
    }

    private fun createViewModel() {
        myRecipesViewModel = ViewModelProvider(this, viewModelFactory)[MyRecipesViewModel::class.java]
    }

    private fun observeLiveData() {
        myRecipesViewModel.getProgressLiveData()
            .observe(viewLifecycleOwner) { isVisible: Boolean -> showProgress(isVisible) }
        myRecipesViewModel.getRecipesLiveData()
            .observe(viewLifecycleOwner) { recipes: List<RecipePresentationModel> ->
                showData(recipes.toMutableList())
            }
        myRecipesViewModel.getErrorLiveData()
            .observe(viewLifecycleOwner) { throwable: Throwable -> showError(throwable) }
    }

    private fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showData(recipes: MutableList<RecipePresentationModel>) {
        adapter = MyRecipesAdapter(recipes, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun showError(throwable: Throwable) {
        throwable.message?.let {
            binding.errorLayout.visibility = View.VISIBLE
            binding.errorText.text = it
        }
    }

    override fun onRecipeClick(recipePresentationModel: RecipePresentationModel) {
        val newIntent = Intent(requireContext(), RecipeDetailActivity::class.java)
        newIntent.putExtra("recipeModel", recipePresentationModel)
        startActivity(newIntent)
    }
}