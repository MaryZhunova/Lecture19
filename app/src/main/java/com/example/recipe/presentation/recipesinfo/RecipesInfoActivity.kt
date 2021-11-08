package com.example.recipe.presentation.recipesinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import com.example.recipe.databinding.RecipesListInfoBinding
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.recipedetail.RecipeDetailActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

/**
 * Активити приложения, которая отображает список рецептов
 */
class RecipesInfoActivity: AppCompatActivity(), RecipesInfoView, OnRecipeClickListener {

    private lateinit var binding: RecipesListInfoBinding
    private lateinit var infoViewModel: RecipesInfoViewModel
    private var query: String? = null
    private lateinit var recipe: List<RecipePresentationModel>
    private lateinit var adapter: RecipesInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecipesListInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showData(emptyList())

        query = intent.getStringExtra("query")
        binding.result.text = getString(R.string.search_result, query)

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        if (savedInstanceState == null) {
            //Отправить сетевой запрос с ключевым словом query через вью модель
            query?.let { infoViewModel.get(it) }
        }
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
        infoViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RecipesInfoViewModel::class.java)) {
                    return NetworkApp.appComponent(applicationContext).getRecipesInfoViewModel() as T
                }
                throw IllegalArgumentException ("UnknownViewModel")
            }
        })[RecipesInfoViewModel::class.java]
    }

    private fun observeLiveData() {
        infoViewModel.getProgressLiveData()
            .observe(this) { isVisible: Boolean -> showProgress(isVisible) }
        infoViewModel.getRecipesLiveData()
            .observe(this) { recipes: List<RecipePresentationModel> ->
                showData(recipes)
                recipe = recipes
            }
        infoViewModel.getErrorLiveData().observe(this) { throwable: Throwable -> showError(throwable) }
    }

    override fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showData(recipes: List<RecipePresentationModel>) {
        adapter = RecipesInfoAdapter(recipes, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    override fun showError(throwable: Throwable) {
        throwable.message?.let {
            binding.errorLayout.visibility = View.VISIBLE
            binding.errorText.text = it
        }
    }

    override fun onRecipeClick(position: Int) {
        val newIntent = Intent(applicationContext, RecipeDetailActivity::class.java)
        newIntent.putExtra("recipe", recipe[position])
        startActivity(newIntent)
    }

    override fun onFavouriteClick(position: Int, pressed: Boolean) {
        if (pressed) {
            Toast.makeText(applicationContext, "Added to favourites", Toast.LENGTH_SHORT).show()
            //todo
        } else {
            Toast.makeText(applicationContext, "Removed from favourites", Toast.LENGTH_SHORT).show()
            //todo
        }
    }
}