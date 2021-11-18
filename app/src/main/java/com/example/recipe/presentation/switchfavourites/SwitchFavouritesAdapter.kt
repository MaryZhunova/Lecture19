package com.example.recipe.presentation.switchfavourites

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recipe.presentation.switchfavourites.favourites.FavouritesFragment
import com.example.recipe.presentation.switchfavourites.myrecipes.MyRecipesFragment

/**
 * Адаптер для отображения фрагментов
 */
class SwitchFavouritesAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) MyRecipesFragment.newInstance()
        else FavouritesFragment.newInstance()
    }

}