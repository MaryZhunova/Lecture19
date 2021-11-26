package com.example.recipe.presentation.switchfavourites.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.databinding.ActivityRecipesInfoBinding
import com.example.recipe.presentation.models.RecipePresentationModel
import com.example.recipe.presentation.switchfavourites.favourites.viewmodel.FavouritesViewModel
import com.example.recipe.presentation.recipedetail.RecipeDetailActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavouritesFragment : Fragment(), OnFavouriteRecipeClickListener {

    private lateinit var binding: ActivityRecipesInfoBinding
    private lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var adapter: FavouritesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityRecipesInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.result.text = getString(R.string.favourite_recipes)

        NetworkApp.appComponent(requireContext()).inject(this)

        initRecyclerview()
        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()
        //Отправить сетевой запрос с ключевым словом query через вью модель
        favouritesViewModel.getFavouriteRecipes()
    }

    private fun initRecyclerview() {
        adapter = FavouritesAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun createViewModel() {
        favouritesViewModel =
            ViewModelProvider(this, viewModelFactory)[FavouritesViewModel::class.java]
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
        adapter.submitList(recipes)
    }

    private fun showError(throwable: Throwable) {
        throwable.message?.let {
            Snackbar.make(binding.root, it, BaseTransientBottomBar.LENGTH_LONG).show()
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