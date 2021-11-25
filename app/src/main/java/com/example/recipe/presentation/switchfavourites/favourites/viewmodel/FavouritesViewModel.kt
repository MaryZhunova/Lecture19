package com.example.recipe.presentation.switchfavourites.favourites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.utils.converters.Converter
import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.RecipePresentationModel
import com.example.recipe.presentation.switchfavourites.favourites.FavouritesFragment
import com.example.recipe.utils.ISchedulersProvider
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ViewModel [FavouritesFragment]
 *
 * @param recipesInteractor интерактор
 * @param schedulersProvider провайдер schedulers
 * @param converterToPresentation конвертер из RecipeDomainModel в RecipePresentationModel
 * @param converterToDomain конвертер из RecipePresentationModel в RecipeDomainModel
 */
class FavouritesViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    private val schedulersProvider: ISchedulersProvider,
    private val converterToPresentation: Converter<RecipeDomainModel, RecipePresentationModel>,
    private val converterToDomain: Converter<RecipePresentationModel, RecipeDomainModel>
) : ViewModel() {

    private val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private val recipesLiveData: MutableLiveData<List<RecipePresentationModel>> = MutableLiveData()
    private val composite: CompositeDisposable = CompositeDisposable()

    /**
     * Получить список избранных рецептов
     */
    fun get() {
        val disposable = Single.fromCallable { recipesInteractor.getFavouriteRecipes() }
            .map { recipes -> recipes.map(converterToPresentation::convert) }
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doAfterTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(recipesLiveData::setValue, errorLiveData::setValue)
        composite.add(disposable)
    }

    /**
     * Удалить рецепт из избранного
     *
     * @param [recipe] рецепт
     */
    fun deleteFromFavourites(recipe: RecipePresentationModel) {
        val completable = object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}
            override fun onComplete() {
                get()
            }
            override fun onError(e: Throwable) {
                errorLiveData.value = e
            }
        }
        Completable.fromRunnable {
            recipesInteractor.deleteFromFavourites(
                converterToDomain.convert(
                    recipe
                )
            )
        }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(completable)
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

    fun getProgressLiveData(): LiveData<Boolean> = progressLiveData
    fun getErrorLiveData(): LiveData<Throwable> = errorLiveData
    fun getRecipesLiveData(): LiveData<List<RecipePresentationModel>> = recipesLiveData
}
