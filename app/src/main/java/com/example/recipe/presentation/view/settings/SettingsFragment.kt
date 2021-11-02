package com.example.recipe.presentation.view.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.recipe.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        preference?.let {
            if (preference.key == "switch_theme") {
                if (preferenceManager.sharedPreferences.getBoolean(it.key, false)) {
                    //dark
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    //light
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, null)
    }

}