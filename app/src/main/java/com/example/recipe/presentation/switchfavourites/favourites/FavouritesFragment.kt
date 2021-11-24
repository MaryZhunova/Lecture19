package com.example.recipe.presentation.switchfavourites.favourites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.databinding.RecipesListInfoBinding
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.switchfavourites.favourites.viewmodel.FavouritesViewModel
import com.example.recipe.presentation.recipedetail.RecipeDetailActivity
import javax.inject.Inject

class FavouritesFragment : Fragment(), OnFavouriteRecipeClickListener {

    private lateinit var binding: RecipesListInfoBinding
    private lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var adapter: FavouritesAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipesListInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showData(mutableListOf())
        binding.result.text = getString(R.string.favourite_recipes)

        NetworkApp.appComponent(requireContext()).inject(this)

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()
        //Отправить сетевой запрос с ключевым словом query через вью модель
        favouritesViewModel.get()
    }

    private fun createViewModel() {
        favouritesViewModel = ViewModelProvider(this, viewModelFactory)[FavouritesViewModel::class.java]
    }

    private fun observeLiveData() {
        favouritesViewModel.getProgressLiveData()
            .observe(viewLifecycleOwner) { isVisible: Boolean -> showProgress(isVisible) }
        favouritesViewModel.getRecipesLiveData()
            .observe(viewLifecycleOwner) { recipes: List<RecipePresentationModel> ->
                showData(recipes.toMutableList())
            }
        favouritesViewModel.getErrorLiveData()
            .observe(viewLifecycleOwner) { throwable: Throwable -> showError(throwable) }
    }

    private fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showData(recipes: MutableList<RecipePresentationModel>) {
        adapter = FavouritesAdapter(recipes, this)
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
        val intent = RecipeDetailActivity.newIntent(requireContext(), recipePresentationModel)
        startActivity(intent)
    }

    override fun onFavouriteClick(recipePresentationModel: RecipePresentationModel) {
        Toast.makeText(requireContext(), "Removed from favourites", Toast.LENGTH_SHORT).show()
        favouritesViewModel.deleteFromFavourites(recipePresentationModel)
    }

    companion object {
        fun newInstance() = FavouritesFragment()
    }
}