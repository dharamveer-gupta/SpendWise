package com.dharamveer.spendwise.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.dharamveer.spendwise.presentation.ui.entry.ExpenseEntryScreen
import com.dharamveer.spendwise.presentation.ui.list.ExpenseListScreen
import com.dharamveer.spendwise.presentation.ui.report.ExpenseReportScreen
import com.dharamveer.spendwise.presentation.ui.settings.SettingsScreen

@Composable
fun SpendWiseNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavItem.items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = NavItem.Entry.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(NavItem.Entry.route) { ExpenseEntryScreen() }
            composable(NavItem.List.route) { ExpenseListScreen() }
            composable(NavItem.Report.route) { ExpenseReportScreen() }
            composable(NavItem.Settings.route) {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
