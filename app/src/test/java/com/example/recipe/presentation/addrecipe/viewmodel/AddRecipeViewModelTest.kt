package com.example.recipe.presentation.addrecipe.viewmodel

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
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

/**
 * Тест проверки работы [AddRecipeViewModel]
 */
class AddRecipeViewModelTest {

    private lateinit var addRecipeViewModel: AddRecipeViewModel
    private val converterToDomain: Converter<MyRecipePresentationModel, MyRecipeDomainModel> = mockk()
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
        addRecipeViewModel =
            AddRecipeViewModel(recipesInteractor, schedulersProvider, converterToDomain)
    }

    @Test
    fun testGetErrorLiveData() {
        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.addToMyRecipes(recipeDomainModel) } throws RuntimeException()

        addRecipeViewModel.addToMyRecipes(recipePresentationModel)

        Truth.assertThat(addRecipeViewModel.getErrorLiveData().value).isInstanceOf(RuntimeException()::class.java)
    }

    @Test
    fun testAddToMyRecipes() {
        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.addToMyRecipes(recipeDomainModel) } returns Unit

        addRecipeViewModel.addToMyRecipes(recipePresentationModel)

        verify {
            recipesInteractor.addToMyRecipes(recipeDomainModel)
        }
        confirmVerified(recipesInteractor)
    }

    @Test
    fun testAddToMyRecipesException() {
        addRecipeViewModel.getErrorLiveData().observeForever(errorLiveDataObserver)

        every { converterToDomain.convert(recipePresentationModel) } returns recipeDomainModel
        every { recipesInteractor.addToMyRecipes(recipeDomainModel) } throws Exception()
        every { errorLiveDataObserver.onChanged(any()) } answers {}

        addRecipeViewModel.addToMyRecipes(recipePresentationModel)

        verify {
            errorLiveDataObserver.onChanged(match { ex ->
                ex is Exception
            })
        }
        confirmVerified(errorLiveDataObserver)
    }

    companion object {
        val recipeDomainModel =
            MyRecipeDomainModel("name", "ingredients", "instructions", "image")

        val recipePresentationModel =
            MyRecipePresentationModel("name", "ingredients", "instructions", "image")
    }
}