package com.example.recipe

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.recipe.presentation.main.MainRecipesActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule var rule = activityScenarioRule<MainRecipesActivity>()

    @Test
    fun changeText_newActivity() {
        Espresso.onView(withId(R.id.edit_query)).perform(
            ViewActions.typeText(STRING),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(withId(R.id.go_btn)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.result))
            .check(ViewAssertions.matches(ViewMatchers.withText("Search results for $STRING")))
    }

    companion object {
        const val STRING = "Fish"
    }
}