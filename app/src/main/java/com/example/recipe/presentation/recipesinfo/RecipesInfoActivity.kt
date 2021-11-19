package com.example.recipe.presentation.recipesinfo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.databinding.RecipesListInfoBinding
import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.recipedetail.RecipeDetailActivity
import javax.inject.Inject

/**
 * Активити приложения, которая отображает список рецептов
 */
class RecipesInfoActivity : AppCompatActivity(), RecipesInfoView, OnRecipeClickListener {

    private lateinit var binding: RecipesListInfoBinding
    private lateinit var infoViewModel: RecipesInfoViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var query: String? = null
    private lateinit var adapter: RecipesInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecipesListInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        NetworkApp.appComponent(applicationContext).inject(this)


        showData(mutableListOf())

        query = intent.getStringExtra("query")
        (getString(R.string.search_result) + " " + query).also { binding.result.text = it }

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        //Отправить сетевой запрос с ключевым словом query через вью модель
        query?.let { infoViewModel.get(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter) {
            //todo
        }
        return true
    }

    private fun createViewModel() {
        infoViewModel = ViewModelProvider(this, viewModelFactory)[RecipesInfoViewModel::class.java]
    }

    private fun observeLiveData() {
        infoViewModel.getProgressLiveData()
            .observe(this) { isVisible: Boolean -> showProgress(isVisible) }
        infoViewModel.getRecipesLiveData()
            .observe(this) { recipes: List<RecipePresentationModel> ->
                if (recipes.isEmpty()) showError(Exception("Couldn't find any recipe"))
                else showData(recipes.toMutableList())
            }
        infoViewModel.getErrorLiveData()
            .observe(this) { throwable: Throwable -> showError(throwable) }
    }

    override fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showData(recipes: MutableList<RecipePresentationModel>) {
        adapter = RecipesInfoAdapter(recipes, this)
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
        newIntent.putExtra("recipe", recipePresentationModel)
        startActivity(newIntent)
    }

    override fun onFavouriteClick(
        recipePresentationModel: RecipePresentationModel,
        pressed: Boolean
    ) {
        if (pressed) {
            Toast.makeText(applicationContext, "Added to favourites", Toast.LENGTH_SHORT).show()
            infoViewModel.addToFavourites(recipePresentationModel)
        } else {
            Toast.makeText(applicationContext, "Removed from favourites", Toast.LENGTH_SHORT).show()
            infoViewModel.deleteFromFavourites(recipePresentationModel)
        }
    }
}