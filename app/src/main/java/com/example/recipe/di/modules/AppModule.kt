package com.example.recipe.di.modules

import com.example.recipe.data.dao.entity.MyRecipeEntity
import com.example.recipe.data.repository.RecipesRepositoryImpl
import com.example.recipe.domain.RecipesRepository
import com.example.recipe.utils.converters.Converter
import com.example.recipe.utils.converters.DomainToPresentationConverter
import com.example.recipe.utils.converters.DomainToRecipeEntityConverter
import com.example.recipe.utils.converters.PresentationToDomainConverter
import com.example.recipe.utils.converters.RecipeEntityToDomainConverter
import com.example.recipe.data.dao.entity.RecipeEntity
import com.example.recipe.utils.converters.MyDomainToMyPresentationConverter
import com.example.recipe.utils.converters.MyDomainToMyRecipeEntityConverter
import com.example.recipe.utils.converters.MyPresentationToMyDomainConverter
import com.example.recipe.utils.converters.MyRecipeEntityToMyDomainConverter
import com.example.recipe.utils.converters.RecipeToDomainConverter
import com.example.recipe.data.network.models.Recipe
import com.example.recipe.domain.models.MyRecipeDomainModel
import com.example.recipe.domain.models.RecipeDomainModel
import com.example.recipe.presentation.models.MyRecipePresentationModel
import com.example.recipe.presentation.models.RecipePresentationModel
import com.example.recipe.utils.ISchedulersProvider
import com.example.recipe.utils.SchedulersProvider
import dagger.Binds
import dagger.Module

@Module
interface AppModule {
    @Binds
    fun provideBaseRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipesRepository

    @Binds
    fun provideConverter(toDomainConverter: RecipeToDomainConverter): Converter<Recipe, RecipeDomainModel>

    @Binds
    fun provideConverter2(domainToPresentationConverter: DomainToPresentationConverter): Converter<RecipeDomainModel, RecipePresentationModel>

    @Binds
    fun provideConverter3(recipeEntityToDomainConverter: RecipeEntityToDomainConverter): Converter<RecipeEntity, RecipeDomainModel>

    @Binds
    fun provideConverter4(domainToRecipeEntityConverter: DomainToRecipeEntityConverter): Converter<RecipeDomainModel, RecipeEntity>

    @Binds
    fun provideConverter5(presentationToDomainConverter: PresentationToDomainConverter): Converter<RecipePresentationModel, RecipeDomainModel>

    @Binds
    fun provideConverter6(domainToMyPresentationConverter: MyDomainToMyPresentationConverter): Converter<MyRecipeDomainModel, MyRecipePresentationModel>

    @Binds
    fun provideConverter7(domainToMyRecipeEntityConverter: MyDomainToMyRecipeEntityConverter): Converter<MyRecipeDomainModel, MyRecipeEntity>

    @Binds
    fun provideConverter8(presentationToMyDomainConverter: MyPresentationToMyDomainConverter): Converter<MyRecipePresentationModel, MyRecipeDomainModel>

    @Binds
    fun provideConverter9(recipeEntityToMyDomainConverter: MyRecipeEntityToMyDomainConverter): Converter<MyRecipeEntity, MyRecipeDomainModel>

    @Binds
    fun provideSchedulers(schedulersProvider: SchedulersProvider): ISchedulersProvider
}
