package com.example.recipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.data.model.Recipe
import com.example.recipe.data.repository.BaseRepository
import com.example.recipe.utils.ISchedulersProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * ViewModel [RecipesInfoActivity]
 */
class RecipesInfoViewModel(private val okhttpRepository: BaseRepository, private val schedulersProvider: ISchedulersProvider) : ViewModel() {

    private val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private val recipesLiveData: MutableLiveData<List<Recipe>> = MutableLiveData()
    private var disposable: Disposable? = null

    /**
     * Получение списка рецептов
     *
     * @param query ключевое слово для запроса
     */
    fun get(query: String) {
        disposable = Single.fromCallable { okhttpRepository.get(query) }
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
    fun getRecipesLiveData(): LiveData<List<Recipe>> = recipesLiveData
}
