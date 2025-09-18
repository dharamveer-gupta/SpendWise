package com.dharamveer.spendwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.dharamveer.spendwise.data.preferences.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeManager: ThemeManager
) : ViewModel() {

    val currentThemeMode: StateFlow<Int> = themeManager.themeMode

    fun setThemeMode(mode: Int) {
        themeManager.setThemeMode(mode)
    }
}