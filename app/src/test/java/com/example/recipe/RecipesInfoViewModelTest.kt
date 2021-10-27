package com.example.recipe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.recipe.data.model.Recipe
import com.example.recipe.data.repository.OkhttpRepository
import com.example.recipe.utils.SchedulersProvider
import com.example.recipe.viewmodel.RecipesInfoViewModel
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
    private val okhttpRepository: OkhttpRepository = mockk()
    private lateinit var recipesInfoViewModel: RecipesInfoViewModel
    private val progressLiveDataObserver: Observer<Boolean> = mockk()
    private val errorLiveDataObserver: Observer<Throwable> = mockk()
    private val recipesLiveDataObserver: Observer<List<Recipe>> = mockk()

    @Rule
    @JvmField
    var mRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { schedulersProvider.io() } returns (Schedulers.trampoline())
        every { schedulersProvider.ui() } returns (Schedulers.trampoline())

        recipesInfoViewModel = RecipesInfoViewModel(okhttpRepository, schedulersProvider)

        recipesInfoViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        recipesInfoViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)
        recipesInfoViewModel.getRecipesLiveData().observeForever(recipesLiveDataObserver)
    }

    @Test
    fun getTest() {
        val expectedResult: List<Recipe> = listOf(Recipe("uri", "label", "image", "source", "url", listOf("ingredientLines")))

        every { okhttpRepository.get(queryArgument) } returns expectedResult
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
        every { okhttpRepository.get(queryArgumentForException) } throws Exception()
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