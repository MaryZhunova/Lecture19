package com.example.recipe.presentation.recipesinfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.utils.converters.Converter
import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.RecipePresentationModel
import com.example.recipe.presentation.recipesinfo.RecipesInfoActivity
import com.example.recipe.utils.ISchedulersProvider
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ViewModel [RecipesInfoActivity]
 *
 * @param recipesInteractor интерактор
 * @param schedulersProvider провайдер schedulers
 * @param converterToPresentation конвертер из RecipeDomainModel в RecipePresentationModel
 * @param converterToDomain конвертер из RecipePresentationModel в RecipeDomainModel
 */
class RecipesInfoViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    private val schedulersProvider: ISchedulersProvider,
    private val converterToPresentation: Converter<RecipeDomainModel, RecipePresentationModel>,
    private val converterToDomain: Converter<RecipePresentationModel, RecipeDomainModel>

) : ViewModel() {

    private val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private val recipesLiveData: MutableLiveData<MutableList<RecipePresentationModel>> =
        MutableLiveData()
    private val nextPageLiveData: MutableLiveData<String> = MutableLiveData()


    private val composite: CompositeDisposable = CompositeDisposable()

    /**
     * Получение списка рецептов
     *
     * @param query ключевое слово для запроса
     */
    fun get(query: String) {
        val disposable = recipesInteractor.get(query)
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doAfterTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe({ result ->
                nextPageLiveData.value = result.first
                recipesLiveData.value =
                    result.second.map(converterToPresentation::convert).toMutableList()
            }, errorLiveData::setValue)
        composite.add(disposable)
    }

    /**
     * Получение списка рецептов
     *
     * @param query ключевое слово для запроса
     * @param cont параметр для перехода на следующую страницу запроса
     */
    fun get(query: String, cont: String?) {
        val disposable = recipesInteractor.get(query, cont!!)
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doAfterTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(
                { result ->
                    nextPageLiveData.value = result.first
                    addList(result.second.map(converterToPresentation::convert))
                },
                errorLiveData::setValue
            )
        composite.add(disposable)
    }

    /**
     * Добавление рецепта в избранное
     *
     * @param recipe рецепт
     */
    fun addToFavourites(recipe: RecipePresentationModel) {
        val completable = object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onComplete() {
            }

            override fun onError(e: Throwable) {
                errorLiveData.value = e
            }
        }
        Completable.fromRunnable {
            recipesInteractor.addToFavourites(
                converterToDomain.convert(
                    recipe
                )
            )
        }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(completable)
    }

    /**
     * Удалить рецепт из избранного
     *
     * @param [recipe] рецепт
     */
    fun deleteFromFavourites(recipe: RecipePresentationModel) {
        val completable = object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onComplete() {
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
    fun getRecipesLiveData(): LiveData<MutableList<RecipePresentationModel>> = recipesLiveData
    fun getNextPageLiveData(): LiveData<String> = nextPageLiveData


    private fun addList(list: List<RecipePresentationModel>) {
        recipesLiveData.value?.addAll(list)
        recipesLiveData.notifyObserver()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}
