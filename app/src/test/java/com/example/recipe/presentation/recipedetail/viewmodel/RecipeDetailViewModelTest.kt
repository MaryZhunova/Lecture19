package com.example.recipe.presentation.recipedetail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.RecipePresentationModel
import com.example.recipe.utils.ISchedulersProvider
import com.example.recipe.utils.converters.Converter
import com.google.common.truth.Truth
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

class RecipeDetailViewModelTest {

    private lateinit var recipeDetailViewModel: RecipeDetailViewModel
    private val converterToDomain: Converter<RecipePresentationModel, RecipeDomainModel> = mockk()
    private val recipesInteractor: RecipesInteractor = mockk()
    private val schedulersProvider: ISchedulersProvider = mockk()
    private val errorLiveDataObserver: Observer<Throwable> = mockk()

    @Rule
    @JvmField
    var mRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { schedulersProvider.io() } returns (Schedulers.trampoline())
        every { schedulersProvider.ui() } returns (Schedulers.trampoline())
        recipeDetailViewModel =
            RecipeDetailViewModel(recipesInteractor, schedulersProvider, converterToDomain)
    }

    @Test
    fun testDeleteFromFavourites() {
        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel

        every { recipesInteractor.deleteFromFavourites(recipeDomainModel) } returns Unit

        recipeDetailViewModel.deleteFromFavourites(recipePresentationModel)

        verify {
            recipesInteractor.deleteFromFavourites(recipeDomainModel)
        }
        confirmVerified(recipesInteractor)
    }

    @Test
    fun testDeleteFromFavouritesException() {
        recipeDetailViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel

        every { recipesInteractor.deleteFromFavourites(recipeDomainModel) } throws Exception()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        recipeDetailViewModel.deleteFromFavourites(recipePresentationModel)

        verify {
            errorLiveDataObserver.onChanged(match { ex ->
                ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)

    }
    @Test
    fun testGetErrorLiveData() {
        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.deleteFromFavourites(recipeDomainModel) } throws RuntimeException()

        recipeDetailViewModel.deleteFromFavourites(recipePresentationModel)

        Truth.assertThat(recipeDetailViewModel.getErrorLiveData().value).isInstanceOf(RuntimeException()::class.java)

    }

    companion object {
        val recipeDomainModel =
            RecipeDomainModel(
                "uri", "label", "image", "source",
                "url", listOf("ingredient"), true)

        val recipePresentationModel =
            RecipePresentationModel(
                "uri", "label", "image", "source",
                "url", listOf("ingredient"), true)
    }
}