package com.example.recipe.presentation.recipesinfo.viewmodel

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
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException


class RecipesInfoViewModelTest {
    private lateinit var recipesInfoViewModel: RecipesInfoViewModel
    private val recipesInteractor: RecipesInteractor = mockk()
    private val schedulersProvider: ISchedulersProvider = mockk()
    private val converterToPresentation: Converter<RecipeDomainModel, RecipePresentationModel> = mockk()
    private val converterToDomain: Converter<RecipePresentationModel, RecipeDomainModel> = mockk()
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
    }

    @Test
    fun testGet() {
        recipesInfoViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        recipesInfoViewModel.getRecipesLiveData().observeForever(recipesLiveDataObserver)
        recipesInfoViewModel.getNextPageLiveData().observeForever(nextPageLiveDataObserver)

        val expectedResult: MutableList<RecipePresentationModel> = mutableListOf(recipePresentationModel)
        val interactorResult: Observable<Pair<String, List<RecipeDomainModel>>> = Observable.just(Pair(
            nextPage, listOf(
            recipeDomainModel)))

        every { recipesInteractor.get(queryArgument) } returns interactorResult
        every { progressLiveDataObserver.onChanged(any()) } answers {}
        every { nextPageLiveDataObserver.onChanged(any()) } answers {}
        every { recipesLiveDataObserver.onChanged(any()) } answers {}
        every { converterToPresentation.convert(recipeDomainModel) } returns recipePresentationModel

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
        recipesInfoViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { recipesInteractor.get(queryArgumentForException) } returns Observable.error(java.lang.Exception())
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
    fun testGetTwoArguments() {
        recipesInfoViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        recipesInfoViewModel.getRecipesLiveData().observeForever(recipesLiveDataObserver)
        recipesInfoViewModel.getNextPageLiveData().observeForever(nextPageLiveDataObserver)

        val interactorResult: Observable<Pair<String, List<RecipeDomainModel>>> = Observable.just(Pair(
            nextPage, listOf(recipeDomainModel)))

        every { recipesInteractor.get(queryArgument, nextPage) } returns interactorResult
        every { progressLiveDataObserver.onChanged(any()) } answers {}
        every { nextPageLiveDataObserver.onChanged(any()) } answers {}
        every { recipesLiveDataObserver.onChanged(any()) } answers {}
        every { converterToPresentation.convert(recipeDomainModel) } returns recipePresentationModel

        recipesInfoViewModel.get(queryArgument, nextPage)

        verifyOrder {
            progressLiveDataObserver.onChanged(true)
            nextPageLiveDataObserver.onChanged(nextPage)
            recipesLiveDataObserver.onChanged(null)
            progressLiveDataObserver.onChanged(false)
        }
        confirmVerified(progressLiveDataObserver, nextPageLiveDataObserver, recipesLiveDataObserver)
    }

    @Test
    fun testGetTwoArgumentsException() {
        recipesInfoViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { recipesInteractor.get(queryArgumentForException, nextPage) } returns Observable.error(java.lang.Exception())
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        recipesInfoViewModel.get(queryArgumentForException, nextPage)

        verify {
            errorLiveDataObserver.onChanged(match {
                    ex -> ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    @Test
    fun testAddToFavourites() {
        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.addToFavourites(recipeDomainModel) } returns Unit

        recipesInfoViewModel.addToFavourites(recipePresentationModel)

        verify {
            recipesInteractor.addToFavourites(recipeDomainModel)
        }
        confirmVerified(recipesInteractor)
    }

    @Test
    fun testAddToFavouritesException() {
        recipesInfoViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.addToFavourites(recipeDomainModel) } throws Exception()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        recipesInfoViewModel.addToFavourites(recipePresentationModel)

        verify {
            errorLiveDataObserver.onChanged(match { ex ->
                ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    @Test
    fun testDeleteFromFavourites() {
        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.deleteFromFavourites(recipeDomainModel) } returns Unit

        recipesInfoViewModel.deleteFromFavourites(recipePresentationModel)

        verify {
            recipesInteractor.deleteFromFavourites(recipeDomainModel)
        }
        confirmVerified(recipesInteractor)
    }

    @Test
    fun testDeleteFromFavouritesException() {
        recipesInfoViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.deleteFromFavourites(recipeDomainModel) } throws Exception()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        recipesInfoViewModel.deleteFromFavourites(recipePresentationModel)

        verify {
            errorLiveDataObserver.onChanged(match { ex ->
                ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    @Test
    fun testGetProgressLiveData() {
        val interactorResult: Observable<Pair<String, List<RecipeDomainModel>>> = Observable.just(Pair(
            nextPage, listOf(recipeDomainModel)))

        every { recipesInteractor.get(queryArgument) } returns interactorResult

        recipesInfoViewModel.get(queryArgument)

        Truth.assertThat(recipesInfoViewModel.getProgressLiveData().value).isEqualTo(true)
    }

    @Test
    fun testGetErrorLiveData() {
        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.addToFavourites(recipeDomainModel) } throws RuntimeException()

        recipesInfoViewModel.addToFavourites(recipePresentationModel)

        Truth.assertThat(recipesInfoViewModel.getErrorLiveData().value).isInstanceOf(
            RuntimeException()::class.java)
    }

    @Test
    fun testGetRecipesLiveData() {
        val expectedResult: MutableList<RecipePresentationModel> = mutableListOf(recipePresentationModel)
        val interactorResult: Observable<Pair<String, List<RecipeDomainModel>>> = Observable.just(Pair(
            nextPage, listOf(recipeDomainModel)))

        every { recipesInteractor.get(queryArgument) } returns interactorResult
        every { converterToPresentation.convert(recipeDomainModel) } returns recipePresentationModel

        recipesInfoViewModel.get(queryArgument)

        Truth.assertThat(recipesInfoViewModel.getRecipesLiveData().value).isEqualTo(expectedResult)
    }

    @Test
    fun testGetNextPageLiveData() {
        val interactorResult: Observable<Pair<String, List<RecipeDomainModel>>> = Observable.just(Pair(
            nextPage, listOf(recipeDomainModel)))

        every { recipesInteractor.get(queryArgument) } returns interactorResult

        recipesInfoViewModel.get(queryArgument)

        Truth.assertThat(recipesInfoViewModel.getNextPageLiveData().value).isEqualTo(nextPage)
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

        const val queryArgument = "chicken"
        const val nextPage = "nextPage"
        const val queryArgumentForException = "chicn"
    }
}