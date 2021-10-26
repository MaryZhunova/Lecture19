package com.example.recipe.presentation.view.recipesinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipe.data.api.Constants
import com.example.recipe.models.data.RecipeR
import com.example.recipe.presentation.viewmodel.RecipesInfoViewModel
import com.example.recipe.data.api.OkHttpRecipesApiImpl
import com.example.recipe.data.api.RecipesApi
import com.example.recipe.data.repository.OkhttpRepository
import com.example.recipe.databinding.RecipesListInfoBinding
import com.example.recipe.domain.BaseRepository
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.converter.DomainToPresentationConverter
import com.example.recipe.models.converter.RecipeToDomainConverter
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.utils.SchedulersProvider
import com.example.recipe.presentation.view.recipedetail.RecipeDetailActivity
import com.example.recipe.utils.ISchedulersProvider
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Активити приложения, которая отображает список рецептов
 */
class RecipesInfoActivity: AppCompatActivity(), RecipesInfoView, OnRecipeClickListener {

    private lateinit var binding: RecipesListInfoBinding
    private lateinit var infoViewModel: RecipesInfoViewModel
    private var query: String? = null
    private lateinit var recipe: List<RecipePresentationModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecipesListInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showData(emptyList())

        query = intent.getStringExtra("query")

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        if (savedInstanceState == null) {
            //Отправить сетевой запрос с ключевым словом query через вью модель
            query?.let { infoViewModel.get(it) }
        }
    }

    private fun createViewModel() {
        val url: HttpUrl = Constants.url.toHttpUrl()
        val httpClient = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .cookieJar(CookieJar.NO_COOKIES)
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .build()
        val okHttpRecipesApiImpl: RecipesApi = OkHttpRecipesApiImpl(httpClient, url)
        val converterToRecipeDomainModel: Converter<RecipeR, RecipeDomainModel> = RecipeToDomainConverter()
        val okhttpRepository: BaseRepository = OkhttpRepository(okHttpRecipesApiImpl, converterToRecipeDomainModel)
        val converterToRecipePresentationModel: Converter<RecipeDomainModel, RecipePresentationModel> = DomainToPresentationConverter()
        val recipesInteractor = RecipesInteractor(okhttpRepository)
        val schedulersProvider: ISchedulersProvider = SchedulersProvider()
        infoViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RecipesInfoViewModel::class.java)) {
                    return RecipesInfoViewModel(recipesInteractor, schedulersProvider, converterToRecipePresentationModel) as T
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
        val adapter = RecipesInfoAdapter(recipes, this)
        binding.recyclerView.adapter = adapter
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(binding.root, "Couldn't find any recipe", BaseTransientBottomBar.LENGTH_LONG).show()
        Log.e("error", throwable.toString())
    }

    override fun onRecipeClick(position: Int) {
        val newIntent = Intent(applicationContext, RecipeDetailActivity::class.java)
        newIntent.putExtra("recipe", recipe[position])
        startActivity(newIntent)
    }
}