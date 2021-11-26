package com.example.recipe.presentation.switchfavourites.myrecipes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.recipe.domain.RecipesInteractor
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.presentation.models.MyRecipePresentationModel
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
 * Тест проверки работы [MyRecipesViewModel]
 */
class MyRecipesViewModelTest {
    private lateinit var myRecipesViewModel: MyRecipesViewModel
    private val recipesInteractor: RecipesInteractor = mockk()
    private val schedulersProvider: ISchedulersProvider = mockk()
    private val converterToPresentation: Converter<MyRecipeDomainModel, MyRecipePresentationModel> = mockk()
    private val converterToDomain: Converter<MyRecipePresentationModel, MyRecipeDomainModel> = mockk()
    private val progressLiveDataObserver: Observer<Boolean> = mockk()
    private val errorLiveDataObserver: Observer<Throwable> = mockk()
    private val myRecipesLiveDataObserver: Observer<List<MyRecipePresentationModel>> = mockk()

    @Rule
    @JvmField
    var mRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { schedulersProvider.io() } returns (Schedulers.trampoline())
        every { schedulersProvider.ui() } returns (Schedulers.trampoline())
        myRecipesViewModel = MyRecipesViewModel(recipesInteractor, schedulersProvider, converterToPresentation, converterToDomain)
    }

    @Test
    fun testGetMyRecipes() {
        myRecipesViewModel.getProgressLiveData().observeForever(progressLiveDataObserver)
        myRecipesViewModel.getRecipesLiveData().observeForever(myRecipesLiveDataObserver)

        val expectedResult: List<MyRecipePresentationModel> = listOf(myRecipePresentationModel)
        val interactorResult: List<MyRecipeDomainModel> = listOf(myRecipeDomainModel)

        every { recipesInteractor.getMyRecipes() } returns interactorResult
        every { progressLiveDataObserver.onChanged(any()) } answers {}
        every { myRecipesLiveDataObserver.onChanged(any()) } answers {}
        every { converterToPresentation.convert(myRecipeDomainModel) } returns myRecipePresentationModel

        myRecipesViewModel.getMyRecipes()

        verifyOrder {
            progressLiveDataObserver.onChanged(true)
            myRecipesLiveDataObserver.onChanged(expectedResult)
            progressLiveDataObserver.onChanged(false)
        }
        confirmVerified(progressLiveDataObserver, myRecipesLiveDataObserver)
    }

    @Test
    fun testGetException() {
        myRecipesViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { recipesInteractor.getMyRecipes() } throws Exception()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        myRecipesViewModel.getMyRecipes()

        verify {
            errorLiveDataObserver.onChanged(match {
                    ex -> ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    @Test
    fun testDeleteFromMyRecipes() {
        every { converterToDomain.convert(myRecipePresentationModel) } returns myRecipeDomainModel
        every { recipesInteractor.deleteFromMyRecipes(myRecipeDomainModel) } returns Unit

        myRecipesViewModel.deleteFromMyRecipes(myRecipePresentationModel)

        verify {
            recipesInteractor.deleteFromMyRecipes(myRecipeDomainModel)
            recipesInteractor.getMyRecipes()
        }
        confirmVerified(recipesInteractor)
    }

    @Test
    fun testDeleteFromFavouritesException() {
        myRecipesViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { converterToDomain.convert(myRecipePresentationModel) } returns myRecipeDomainModel
        every { recipesInteractor.deleteFromMyRecipes(myRecipeDomainModel) } throws java.lang.Exception()

        every { errorLiveDataObserver.onChanged(any()) } answers {}

        myRecipesViewModel.deleteFromMyRecipes(myRecipePresentationModel)

        verify {
            errorLiveDataObserver.onChanged(match { ex ->
                ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    @Test
    fun testGetProgressLiveData() {
        val interactorResult: List<MyRecipeDomainModel> = listOf(myRecipeDomainModel)

        every { recipesInteractor.getMyRecipes() } returns interactorResult

        myRecipesViewModel.getMyRecipes()

        Truth.assertThat(myRecipesViewModel.getProgressLiveData().value).isEqualTo(false)
    }

    @Test
    fun testGetErrorLiveData() {
        every { recipesInteractor.getMyRecipes() } throws  RuntimeException()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        myRecipesViewModel.getMyRecipes()

        Truth.assertThat(myRecipesViewModel.getErrorLiveData().value).isInstanceOf(
            RuntimeException()::class.java)
    }

    @Test
    fun testGetRecipesLiveData() {
        val expectedResult: List<MyRecipePresentationModel> = listOf(myRecipePresentationModel)
        val interactorResult: List<MyRecipeDomainModel> = listOf(myRecipeDomainModel)

        every { recipesInteractor.getMyRecipes() } returns interactorResult
        every { converterToPresentation.convert(myRecipeDomainModel) } returns myRecipePresentationModel

        myRecipesViewModel.getMyRecipes()

        Truth.assertThat(myRecipesViewModel.getRecipesLiveData().value).isEqualTo(expectedResult)
    }

    companion object {
        val myRecipeDomainModel = MyRecipeDomainModel("name", "ingredients", "instructions", "image")
        val myRecipePresentationModel = MyRecipePresentationModel("name", "ingredients", "instructions", "image")
    }
}