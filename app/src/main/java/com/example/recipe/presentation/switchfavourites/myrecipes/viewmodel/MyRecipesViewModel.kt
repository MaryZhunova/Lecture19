package com.example.recipe.presentation.switchfavourites.myrecipes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.utils.converters.Converter
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.presentation.models.MyRecipePresentationModel
import com.example.recipe.presentation.switchfavourites.myrecipes.MyRecipesFragment
import com.example.recipe.utils.ISchedulersProvider
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ViewModel [MyRecipesFragment]
 */
class MyRecipesViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    private val schedulersProvider: ISchedulersProvider,
    private val converterToPresentation: Converter<MyRecipeDomainModel, MyRecipePresentationModel>,
    private val converterToDomain: Converter<MyRecipePresentationModel, MyRecipeDomainModel>
) : ViewModel() {

    private val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private val recipesLiveData: MutableLiveData<List<MyRecipePresentationModel>> =
        MutableLiveData()
    private val composite: CompositeDisposable = CompositeDisposable()

    /**
     * Получить список рецептов из базы данных
     */
    fun getMyRecipes() {
        val disposable = Single.fromCallable { recipesInteractor.getMyRecipes() }
            .map { recipes -> recipes.map(converterToPresentation::convert) }
            .doOnSubscribe { progressLiveData.postValue(true) }
            .doAfterTerminate { progressLiveData.postValue(false) }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(recipesLiveData::setValue, errorLiveData::setValue)
        composite.add(disposable)
    }

    /**
     * Удалить рецепт из базы данных
     *
     * @param [recipe] рецепт
     */
    fun deleteFromMyRecipes(recipe: MyRecipePresentationModel) {
        val completable = object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {}
            override fun onComplete() {
                getMyRecipes()
            }
            override fun onError(e: Throwable) {
                errorLiveData.value = e
            }
        }
        Completable.fromRunnable {
            recipesInteractor.deleteFromMyRecipes(
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
    fun getRecipesLiveData(): LiveData<List<MyRecipePresentationModel>> = recipesLiveData
}
