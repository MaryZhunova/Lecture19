package com.example.recipe.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.view.recipesinfo.RecipesInfoActivity
import com.example.recipe.utils.ISchedulersProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * ViewModel [RecipesInfoActivity]
 */
class RecipesInfoViewModel(private val recipesInteractor: RecipesInteractor,
                           private val schedulersProvider: ISchedulersProvider,
                           private val converter: Converter<RecipeDomainModel, RecipePresentationModel>) : ViewModel() {

    private val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private val recipesLiveData: MutableLiveData<List<RecipePresentationModel>> = MutableLiveData()
    private var disposable: Disposable? = null

    /**
     * Получение списка рецептов
     *
     * @param query ключевое слово для запроса
     */
    fun get(query: String) {
        disposable = Single.fromCallable { recipesInteractor.get(query) }
            .map { recipes -> recipes.map(converter::convert) }
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doAfterTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(recipesLiveData::setValue, errorLiveData::setValue)
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
    }

    fun getProgressLiveData(): LiveData<Boolean> = progressLiveData
    fun getErrorLiveData(): LiveData<Throwable> = errorLiveData
    fun getRecipesLiveData(): LiveData<List<RecipePresentationModel>> = recipesLiveData
}
