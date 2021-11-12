package com.example.recipe.presentation.favourites

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.databinding.RecipesListInfoBinding
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.favourites.viewmodel.FavouritesViewModel
import com.example.recipe.presentation.recipedetail.RecipeDetailActivity

/**
 * Активити, которая отображает список избранных рецептов
 */
class FavouritesActivity : AppCompatActivity(), FavouritesView, OnFavouriteRecipeClickListener {

    private lateinit var binding: RecipesListInfoBinding
    private lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var adapter: FavouritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecipesListInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        showData(mutableListOf())
        binding.result.text = getString(R.string.favourite_recipes)

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        //Отправить сетевой запрос с ключевым словом query через вью модель
        favouritesViewModel.get()
    }

    private fun createViewModel() {
        favouritesViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
                    return NetworkApp.appComponent(applicationContext).getFavouritesViewModel() as T
                }
                throw IllegalArgumentException("UnknownViewModel")
            }
        })[FavouritesViewModel::class.java]
    }

    private fun observeLiveData() {
        favouritesViewModel.getProgressLiveData()
            .observe(this) { isVisible: Boolean -> showProgress(isVisible) }
        favouritesViewModel.getRecipesLiveData()
            .observe(this) { recipes: List<RecipePresentationModel> ->
                showData(recipes.toMutableList())
            }
        favouritesViewModel.getErrorLiveData()
            .observe(this) { throwable: Throwable -> showError(throwable) }
    }

    override fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showData(recipes: MutableList<RecipePresentationModel>) {
        adapter = FavouritesAdapter(recipes, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun showError(throwable: Throwable) {
        throwable.message?.let {
            binding.errorLayout.visibility = View.VISIBLE
            binding.errorText.text = it
        }
    }

    override fun onRecipeClick(recipePresentationModel: RecipePresentationModel) {
        val newIntent = Intent(applicationContext, RecipeDetailActivity::class.java)
        newIntent.putExtra("recipeModel", recipePresentationModel)
        startActivity(newIntent)
    }

    override fun onFavouriteClick(recipePresentationModel: RecipePresentationModel) {
        Toast.makeText(applicationContext, "Removed from favourites", Toast.LENGTH_SHORT).show()
        favouritesViewModel.deleteFromFavourites(recipePresentationModel)
    }
}