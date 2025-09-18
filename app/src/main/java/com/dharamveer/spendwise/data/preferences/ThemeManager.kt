package com.dharamveer.spendwise.data.preferences

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    private val themePreferences: ThemePreferences
) {
    private val _themeMode = MutableStateFlow(themePreferences.getThemeMode())
    val themeMode: StateFlow<Int> = _themeMode.asStateFlow()

    fun setThemeMode(mode: Int) {
        themePreferences.setThemeMode(mode)
        _themeMode.value = mode
    }

    @Composable
    fun isDarkTheme(): Boolean {
        val currentThemeMode by themeMode.collectAsState()
        return when (currentThemeMode) {
            ThemePreferences.THEME_MODE_LIGHT -> false
            ThemePreferences.THEME_MODE_DARK -> true
            else -> isSystemInDarkTheme()
        }
    }

    fun getThemeModeName(mode: Int): String {
        return when (mode) {
            ThemePreferences.THEME_MODE_LIGHT -> "Light"
            ThemePreferences.THEME_MODE_DARK -> "Dark"
            else -> "System"
        }
    }
}