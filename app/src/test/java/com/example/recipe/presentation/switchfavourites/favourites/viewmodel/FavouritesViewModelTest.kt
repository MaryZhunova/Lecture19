package com.example.recipe.presentation.switchfavourites.favourites.viewmodel

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
import io.mockk.verifyOrder
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

/**
 * Тест проверки работы [FavouritesViewModel]
 */
class FavouritesViewModelTest {
    private lateinit var favouritesViewModel: FavouritesViewModel
    private val recipesInteractor: RecipesInteractor = mockk()
    private val schedulersProvider: ISchedulersProvider = mockk()
    private val converterToPresentation: Converter<RecipeDomainModel, RecipePresentationModel> = mockk()
    private val converterToDomain: Converter<RecipePresentationModel, RecipeDomainModel> = mockk()
    private val progressLiveDataObserver: Observer<Boolean> = mockk()
    private val errorLiveDataObserver: Observer<Throwable> = mockk()
    private val recipesLiveDataObserver: Observer<List<RecipePresentationModel>> = mockk()

    @Rule
    @JvmField
    var mRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { schedulersProvider.io() } returns (Schedulers.trampoline())
        every { schedulersProvider.ui() } returns (Schedulers.trampoline())
        favouritesViewModel = FavouritesViewModel(recipesInteractor, schedulersProvider, converterToPresentation, converterToDomain)
    }

    @Test
    fun testGetFavouriteRecipes() {
        favouritesViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        favouritesViewModel.getRecipesLiveData().observeForever(recipesLiveDataObserver)

        val expectedResult: List<RecipePresentationModel> = listOf(recipePresentationModel)
        val interactorResult: List<RecipeDomainModel> = listOf(recipeDomainModel)

        every { recipesInteractor.getFavouriteRecipes() } returns interactorResult
        every { progressLiveDataObserver.onChanged(any()) } answers {}
        every { recipesLiveDataObserver.onChanged(any()) } answers {}
        every { converterToPresentation.convert(recipeDomainModel) } returns recipePresentationModel

        favouritesViewModel.getFavouriteRecipes()

        verifyOrder {
            progressLiveDataObserver.onChanged(true)
            recipesLiveDataObserver.onChanged(expectedResult)
            progressLiveDataObserver.onChanged(false)
        }
        confirmVerified(progressLiveDataObserver, recipesLiveDataObserver)
    }
    @Test
    fun testGetFavouriteRecipesException() {
        favouritesViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { recipesInteractor.getFavouriteRecipes() } throws  Exception()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        favouritesViewModel.getFavouriteRecipes()

        verify {
            errorLiveDataObserver.onChanged(match {
                    ex -> ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    @Test
    fun testDeleteFromFavourites() {
        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.deleteFromFavourites(recipeDomainModel) } returns Unit

        favouritesViewModel.deleteFromFavourites(recipePresentationModel)

        verify {
            recipesInteractor.deleteFromFavourites(recipeDomainModel)
            recipesInteractor.getFavouriteRecipes()
        }
        confirmVerified(recipesInteractor)
    }

    @Test
    fun testDeleteFromFavouritesException() {
        favouritesViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.deleteFromFavourites(recipeDomainModel) } throws Exception()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        favouritesViewModel.deleteFromFavourites(recipePresentationModel)

        verify {
            errorLiveDataObserver.onChanged(match { ex ->
                ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    @Test
    fun testGetProgressLiveData() {
        val interactorResult: List<RecipeDomainModel> = listOf(recipeDomainModel)

        every { recipesInteractor.getFavouriteRecipes() } returns interactorResult

        favouritesViewModel.getFavouriteRecipes()

        Truth.assertThat(favouritesViewModel.getProgressLiveData().value).isEqualTo(false)
    }

    @Test
    fun testGetErrorLiveData() {
        every { recipesInteractor.getFavouriteRecipes() } throws  RuntimeException()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        favouritesViewModel.getFavouriteRecipes()

        Truth.assertThat(favouritesViewModel.getErrorLiveData().value).isInstanceOf(
            RuntimeException()::class.java)
    }

    @Test
    fun testGetRecipesLiveData() {
        val expectedResult: List<RecipePresentationModel> = listOf(recipePresentationModel)
        val interactorResult: List<RecipeDomainModel> = listOf(recipeDomainModel)

        every { recipesInteractor.getFavouriteRecipes() } returns interactorResult
        every { converterToPresentation.convert(recipeDomainModel) } returns recipePresentationModel

        favouritesViewModel.getFavouriteRecipes()

        Truth.assertThat(favouritesViewModel.getRecipesLiveData().value).isEqualTo(expectedResult)
    }

    private companion object {
        val recipeDomainModel = RecipeDomainModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredient"), true
        )
        val recipePresentationModel = RecipePresentationModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredient"), true
        )
    }
}