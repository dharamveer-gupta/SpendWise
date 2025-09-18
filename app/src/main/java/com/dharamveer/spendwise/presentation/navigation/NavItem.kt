package com.dharamveer.spendwise.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(val route: String, val label: String, val icon: ImageVector) {
    object Entry : NavItem("entry", "Add", Icons.Default.Add)
    object List : NavItem("list", "List", Icons.AutoMirrored.Filled.List)
    object Report : NavItem("report", "Report", Icons.Default.PieChart)
    object Settings : NavItem("settings", "Settings", Icons.Default.Settings)

    companion object {
        val items = listOf(Entry, List, Report, Settings)
    }
}
