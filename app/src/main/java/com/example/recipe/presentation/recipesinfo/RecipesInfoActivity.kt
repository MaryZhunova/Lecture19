package com.example.recipe.presentation.recipesinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipe.NetworkApp
import com.example.recipe.R
import com.example.recipe.databinding.ActivityRecipesInfoBinding
import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import com.example.recipe.presentation.models.RecipePresentationModel
import com.example.recipe.presentation.recipedetail.RecipeDetailActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Активити приложения, которая отображает список рецептов по запросу
 */
class RecipesInfoActivity : AppCompatActivity(), OnRecipeClickListener {

    private lateinit var binding: ActivityRecipesInfoBinding
    private lateinit var infoViewModel: RecipesInfoViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var query: String? = null
    private lateinit var adapter: RecipesInfoAdapter
    var size = 0
    var nextPage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipesInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        NetworkApp.appComponent(applicationContext).inject(this)

        query = intent.getStringExtra(QUERY)
        (getString(R.string.search_result) + " " + query).also { binding.result.text = it }

        initRecyclerview()
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

    private fun initRecyclerview() {
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
    }

    private fun createViewModel() {
        infoViewModel = ViewModelProvider(this, viewModelFactory)[RecipesInfoViewModel::class.java]
    }

    private fun observeLiveData() {
        infoViewModel.getProgressLiveData()
            .observe(this) { isVisible: Boolean -> showProgress(isVisible) }
        infoViewModel.getRecipesLiveData()
            .observe(this) { recipes: MutableList<RecipePresentationModel> ->
                if (recipes.isEmpty()) {
                    showError(Exception("Couldn't find any recipe"))
                } else {
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

    private fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility =
            if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showData(recipes: MutableList<RecipePresentationModel>) {
        adapter.update(recipes)
    }

    private fun showError(throwable: Throwable) {
        throwable.message?.let {
            Snackbar.make(binding.root, it, BaseTransientBottomBar.LENGTH_LONG).show()
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

    companion object {
        private const val QUERY = "query"
        fun newIntent(context: Context, query: String): Intent {
            val intent = Intent(context, RecipesInfoActivity::class.java)
            intent.putExtra(QUERY, query)
            return intent
        }
    }
}

