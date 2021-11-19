package com.example.recipe.presentation.recipesinfo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipe.NetworkApp
import com.example.recipe.databinding.RecipesListInfoBinding
import com.example.recipe.presentation.recipesinfo.viewmodel.RecipesInfoViewModel
import com.example.recipe.models.presentation.RecipePresentationModel

/**
 * Активити приложения, которая отображает список рецептов
 */
class RecipesInfoActivity : AppCompatActivity(), RecipesInfoView {

    private lateinit var binding: RecipesListInfoBinding
    private lateinit var infoViewModel: RecipesInfoViewModel
    private var query: String? = null
    private lateinit var adapter: RecipesInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RecipesListInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        showData(mutableListOf())

        query = intent.getStringExtra("query")

        //Создать вью модель
        createViewModel()
        //Наблюдать за LiveData
        observeLiveData()

        //Отправить сетевой запрос с ключевым словом query через вью модель
        query?.let { infoViewModel.get(it) }
    }

    private fun createViewModel() {
        infoViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RecipesInfoViewModel::class.java)) {
                    return NetworkApp.appComponent(applicationContext)
                        .getRecipesInfoViewModel() as T
                }
                throw IllegalArgumentException("UnknownViewModel")
            }
        })[RecipesInfoViewModel::class.java]
    }

    private fun observeLiveData() {
        infoViewModel.getProgressLiveData()
            .observe(this) { isVisible: Boolean -> showProgress(isVisible) }
        infoViewModel.getRecipesLiveData()
            .observe(this) { recipes: List<RecipePresentationModel> ->
                showData(recipes.toMutableList())
            }
        infoViewModel.getErrorLiveData()
            .observe(this) { throwable: Throwable -> showError(throwable) }
    }

    override fun showProgress(isVisible: Boolean) {
        binding.progressFrameLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun showData(recipes: MutableList<RecipePresentationModel>) {
        adapter = RecipesInfoAdapter(recipes)
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
}