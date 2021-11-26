package com.example.recipe.presentation.addrecipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.utils.converters.Converter
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.presentation.models.MyRecipePresentationModel
import com.example.recipe.presentation.addrecipe.AddRecipeActivity
import com.example.recipe.utils.ISchedulersProvider
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ViewModel [AddRecipeActivity]
 *
 * @param recipesInteractor интерактор
 * @param schedulersProvider провайдер schedulers
 * @param converterToDomain конвертер из MyRecipePresentationModel в MyRecipeDomainModel
 */
class AddRecipeViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    private val schedulersProvider: ISchedulersProvider,
    private val converterToDomain: Converter<MyRecipePresentationModel, MyRecipeDomainModel>
) : ViewModel() {

    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    /**
     * Добавить рецепт в базу данных
     *
     * @param [recipe] рецепт
     */
    fun addToMyRecipes(recipe: MyRecipePresentationModel) {
        val completable = object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}
            override fun onComplete() {}
            override fun onError(e: Throwable) {
                errorLiveData.value = e
            }
        }
        Completable.fromRunnable {
            recipesInteractor.addToMyRecipes(
                converterToDomain.convert(
                    recipe)
            )
        }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(completable)
    }

    fun getErrorLiveData(): LiveData<Throwable> = errorLiveData
}
