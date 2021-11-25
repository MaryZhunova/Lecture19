package com.example.recipe.presentation.switchfavourites.addrecipe.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.domain.MyRecipeDomainModel
import com.example.recipe.models.presentation.MyRecipePresentationModel
import com.example.recipe.presentation.switchfavourites.SwitchFavouritesActivity
import com.example.recipe.utils.ISchedulersProvider
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import java.io.File
import java.util.UUID
import javax.inject.Inject

/**
 * ViewModel [SwitchFavouritesActivity]
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

    val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
//    val currentImageUri: MutableLiveData<Uri> = MutableLiveData()

    /**
     * Добавить рецепт в базу данных
     *
     * @param [recipe] рецепт
     */
    fun addToMyRecipes(recipe: MyRecipePresentationModel) {
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
            recipesInteractor.addToMyRecipes(
                converterToDomain.convert(
                    recipe
                )
            )
        }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribe(completable)
    }

    fun getErrorLiveData(): LiveData<Throwable> = errorLiveData
//    fun getCurrentImageUri(): LiveData<Uri> = currentImageUri
}
