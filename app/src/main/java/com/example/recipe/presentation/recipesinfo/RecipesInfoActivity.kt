package com.example.recipe.presentation.recipesinfo

import android.content.Context
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
import androidx.recyclerview.widget.RecyclerView
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
    var size = 0
    var nextPage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecipesListInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        NetworkApp.appComponent(applicationContext).inject(this)

        adapter = RecipesInfoAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        query = intent.getStringExtra(QUERY)
        (getString(R.string.search_result) + " " + query).also { binding.result.text = it }

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        binding.recyclerView.addOnScrollListener(MyOnScrollListener())

        //Отправить сетевой запрос с ключевым словом query через вью модель
        if (savedInstanceState == null) {
            query?.let { infoViewModel.get(it) }
        }
    }

    inner class MyOnScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == size - 1) {
                if (nextPage.isNotEmpty()) {
                    query?.let { infoViewModel.get(it, nextPage) }
                }
            }
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
        infoViewModel = ViewModelProvider(this, viewModelFactory)[RecipesInfoViewModel::class.java]
    }

    private fun observeLiveData() {
        infoViewModel.getProgressLiveData()
            .observe(this) { isVisible: Boolean -> showProgress(isVisible) }
        infoViewModel.getRecipesLiveData()
            .observe(this) { recipes: MutableList<RecipePresentationModel> ->
                if (recipes.isEmpty()) showError(Exception("Couldn't find any recipe"))
                else {
                    size = recipes.size
                    showData(recipes)
                }
            }
        infoViewModel.getErrorLiveData()
            .observe(this) { throwable: Throwable -> showError(throwable) }
        infoViewModel.getNextPageLiveData().observe(this) { nextPage ->
            this.nextPage = nextPage
        }
    }

    override fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showData(recipes: MutableList<RecipePresentationModel>) {
        adapter.update(recipes)
    }

    override fun showError(throwable: Throwable) {
        throwable.message?.let {
            binding.errorLayout.visibility = View.VISIBLE
            binding.errorText.text = it
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRecipeClick(recipePresentationModel: RecipePresentationModel) {
        val intent = RecipeDetailActivity.newIntent(this, recipePresentationModel)
        startActivity(intent)
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

    companion object {
        private const val QUERY = "query"
        fun newIntent(context: Context, query: String): Intent {
            val intent = Intent(context, RecipesInfoActivity::class.java)
            intent.putExtra(QUERY, query)
            return intent
        }
    }
}

