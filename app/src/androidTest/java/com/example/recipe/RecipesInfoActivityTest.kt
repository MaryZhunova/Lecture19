package com.example.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.recipe.models.presentation.RecipePresentationModel
import com.example.recipe.presentation.recipesinfo.RecipesInfoActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RecipesInfoActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(RecipesInfoActivity::class.java)

    @Before
    fun setup() {
    }

    @Test
    fun progressbarIsVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.progress_frame_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

//    @Test
//    fun givenPlantListFragment_whenLoadingVisibilityVisible_thenLoadingIsShown() {
//
//        /* Given */
//        plantListLiveData.postValue(Resource.success(fakePlantList))
//        whenever(viewModel.getContentVisibility()).thenReturn(View.GONE)
//        whenever(viewModel.getLoadingVisibility()).thenReturn(View.VISIBLE)
//
//        /* When */
//        activityRule.activity.setFragment(plantListFragment)
//
//        /* Then */
//        onView(withId(R.id.plant_list_recyclerview)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.progress)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun givenPlantListFragment_whenPlantListItemClicked_thenPlantItemClickedCalled() {
//
//        /* Given */
//        plantListLiveData.postValue(Resource.success(fakePlantList))
//        whenever(viewModel.getContentVisibility()).thenReturn(View.VISIBLE)
//        whenever(viewModel.getLoadingVisibility()).thenReturn(View.GONE)
//
//        /* When */
//        activityRule.activity.setFragment(plantListFragment)
//        onView(withId(R.id.plant_list_recyclerview)).perform(
//            RecyclerViewActions
//                .actionOnItemAtPosition<PlantListAdapter.PlantViewHolder>(
//                    1,
//                    click()
//                )
//        )
//
//        /* Then */
//        verify(viewModel, times(1)).plantItemClicked()
//    }
//
//    private val fakePlantList = listOf(
//        Plant(
//            "Aloe Vera"
//        ),
//        Plant(
//            "Devil's Ivy"
//        ),
//        Plant(
//            "Peace Lily"
//        ),
//        Plant(
//            "Jade Plant"
//        ),
//        Plant(
//            "Spider Plant"
//        )
//    )
//
//    class TestPlantListFragment : PlantListFragment() {
//        override fun initializeViewModelFactory() { /* no-op*/
//        }
//    }

    companion object {
        val mockData: LiveData<List<RecipePresentationModel>> = MutableLiveData(
            listOf(
                RecipePresentationModel(
                    "uri",
                    "Burnt-Scallion Fish",
                    "https://www.edamam.com/web-img/a96/a967fbe797803dbe8418a668cf304b53.jpg",
                    "source",
                    "url",
                    listOf("ingredientLines")
                ), RecipePresentationModel(
                    "uri",
                    "Fish Fumet recipes",
                    "https://www.edamam.com/web-img/eb5/eb58edb23bf93ebc4ea94abdd95c14ee",
                    "source",
                    "url",
                    listOf("ingredientLines")
                ), RecipePresentationModel(
                    "uri",
                    "Fish Head Curry",
                    "https://www.edamam.com/web-img/453/45377df0aa09dfc2b19bdaad0dda0683.jpg",
                    "source",
                    "url",
                    listOf("ingredientLines")
                )
            )
        )
        val visible: LiveData<Boolean> = MutableLiveData(true)
        val invisible: LiveData<Boolean> = MutableLiveData(false)
    }
}