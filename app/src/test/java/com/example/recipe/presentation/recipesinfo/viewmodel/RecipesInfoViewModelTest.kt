package com.example.recipe.presentation.recipesinfo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.recipe.data.dao.entity.IngredientEntity
import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.RecipePresentationModel
import com.example.recipe.utils.ISchedulersProvider
import com.example.recipe.utils.converters.Converter
import com.example.recipe.utils.converters.DomainToPresentationConverter
import com.example.recipe.utils.converters.PresentationToDomainConverter
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RecipesInfoViewModelTest {
    private lateinit var recipesInfoViewModel: RecipesInfoViewModel
    private val recipesInteractor: RecipesInteractor = mockk()
    private val schedulersProvider: ISchedulersProvider = mockk()
    private val converterToPresentation: Converter<RecipeDomainModel, RecipePresentationModel> = //mockk()
        DomainToPresentationConverter()
    private val converterToDomain: Converter<RecipePresentationModel, RecipeDomainModel> = //mockk()
        PresentationToDomainConverter()
    private val progressLiveDataObserver: Observer<Boolean> = mockk()
    private val errorLiveDataObserver: Observer<Throwable> = mockk()
    private val recipesLiveDataObserver: Observer<MutableList<RecipePresentationModel>> = mockk()
    private val nextPageLiveDataObserver: Observer<String> = mockk()

    @Rule
    @JvmField
    var mRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { schedulersProvider.io() } returns (Schedulers.trampoline())
        every { schedulersProvider.ui() } returns (Schedulers.trampoline())
        recipesInfoViewModel = RecipesInfoViewModel(recipesInteractor, schedulersProvider, converterToPresentation, converterToDomain)

        recipesInfoViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        recipesInfoViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)
        recipesInfoViewModel.getRecipesLiveData().observeForever(recipesLiveDataObserver)
        recipesInfoViewModel.getNextPageLiveData().observeForever(nextPageLiveDataObserver)
    }

    @Test
    fun testGet() {
        val expectedResult: MutableList<RecipePresentationModel> = mutableListOf(recipePresentationModel)
        val interactorResult: Observable<Pair<String, List<RecipeDomainModel>>> = Observable.just(Pair(
            nextPage, listOf(
            recipeDomainModel)))

        every { recipesInteractor.get(queryArgument) } returns interactorResult
        every { progressLiveDataObserver.onChanged(any()) } answers {}
        every { nextPageLiveDataObserver.onChanged(any()) } answers {}
        every { recipesLiveDataObserver.onChanged(any()) } answers {}


        recipesInfoViewModel.get(queryArgument)

        verifyOrder {
            progressLiveDataObserver.onChanged(true)
            nextPageLiveDataObserver.onChanged(nextPage)
            recipesLiveDataObserver.onChanged(expectedResult)
            progressLiveDataObserver.onChanged(false)
        }
        confirmVerified(progressLiveDataObserver, nextPageLiveDataObserver, recipesLiveDataObserver)
    }

    @Test
    fun testGetException() {
        every { recipesInteractor.get(queryArgumentForException) } throws Exception()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        recipesInfoViewModel.get(queryArgumentForException)

        verify {
            errorLiveDataObserver.onChanged(match {
                ex -> ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    @Test
    fun testTestGet() {}

    @Test
    fun testAddToFavourites() {}

    @Test
    fun testDeleteFromFavourites() {}

    @Test
    fun testOnCleared() {}

    @Test
    fun testGetProgressLiveData() {}

    @Test
    fun testGetErrorLiveData() {}

    @Test
    fun testGetRecipesLiveData() {}

    @Test
    fun testGetNextPageLiveData() {}

    private companion object {
        val recipeDomainModel = RecipeDomainModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredient"), true
        )
        val recipePresentationModel = RecipePresentationModel(
            "uri", "label", "image", "source",
            "url", listOf("ingredient"), true
        )

        const val queryArgument = "chicken"
        const val nextPage = "nextPage"
        const val queryArgumentForException = "chicn"
    }
}