package com.dharamveer.spendwise.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "theme_preferences",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_THEME_MODE = "theme_mode"
        const val THEME_MODE_SYSTEM = 0
        const val THEME_MODE_LIGHT = 1
        const val THEME_MODE_DARK = 2
    }

    fun getThemeMode(): Int {
        return prefs.getInt(KEY_THEME_MODE, THEME_MODE_SYSTEM)
    }

    fun setThemeMode(themeMode: Int) {
        prefs.edit().putInt(KEY_THEME_MODE, themeMode).apply()
    }
}