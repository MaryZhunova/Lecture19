package com.example.recipe.presentation.switchfavourites.myrecipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.switchfavourites.myrecipes.MyRecipesFragment
import com.example.recipe.utils.ISchedulersProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * ViewModel [MyRecipesFragment]
 */
class MyRecipesViewModel@Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    private val schedulersProvider: ISchedulersProvider,
    private val converterToPresentation: Converter<RecipeDomainModel, RecipePresentationModel>
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
    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }

    fun getProgressLiveData(): LiveData<Boolean> = progressLiveData
    fun getErrorLiveData(): LiveData<Throwable> = errorLiveData
    fun getRecipesLiveData(): LiveData<List<RecipePresentationModel>> = recipesLiveData
}
