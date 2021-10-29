package com.example.recipe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.models.converter.Converter
import com.example.recipe.models.converter.DomainToPresentationConverter
import com.example.recipe.models.domain.RecipeDomainModel
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.viewmodel.RecipesInfoViewModel
import com.example.recipe.utils.SchedulersProvider
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RecipesInfoViewModelTest {
    private val schedulersProvider: SchedulersProvider = mockk()
    private val recipesInteractor: RecipesInteractor = mockk()
    private lateinit var converter: Converter<RecipeDomainModel, RecipePresentationModel>
    private lateinit var recipesInfoViewModel: RecipesInfoViewModel
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

        converter = DomainToPresentationConverter()
        recipesInfoViewModel = RecipesInfoViewModel(recipesInteractor, schedulersProvider, converter)

        recipesInfoViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        recipesInfoViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)
        recipesInfoViewModel.getRecipesLiveData().observeForever(recipesLiveDataObserver)
    }

    @Test
    fun getTest() {
        val recipesInteractorResult: List<RecipeDomainModel> = listOf(RecipeDomainModel("uri", "label", "image", "source", "url", listOf("ingredientLines")))
        val expectedResult: List<RecipePresentationModel> = listOf(RecipePresentationModel("uri", "label", "image", "source", "url", listOf("ingredientLines")))

        every { recipesInteractor.get(queryArgument) } returns recipesInteractorResult
        every { progressLiveDataObserver.onChanged(any()) } answers {}
        every { recipesLiveDataObserver.onChanged(any()) } answers {}


        recipesInfoViewModel.get(queryArgument)

        verifyOrder {
            progressLiveDataObserver.onChanged(true)
            recipesLiveDataObserver.onChanged(expectedResult)
            progressLiveDataObserver.onChanged(false)
        }
        confirmVerified(progressLiveDataObserver, recipesLiveDataObserver)
    }
    @Test
    fun getTestException() {
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

    private companion object {
        const val queryArgument = "Nyama"
        const val queryArgumentForException = "nyam"
    }
}