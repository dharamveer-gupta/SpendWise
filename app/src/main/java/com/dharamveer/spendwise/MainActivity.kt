package com.dharamveer.spendwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dharamveer.spendwise.presentation.ui.entry.ExpenseEntryScreen
import com.dharamveer.spendwise.presentation.ui.list.ExpenseListScreen
import com.dharamveer.spendwise.presentation.ui.report.ExpenseReportScreen
import com.dharamveer.spendwise.ui.theme.SpendWiseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SpendWiseTheme {
//                ExpenseEntryScreen()
//                ExpenseListScreen()
                ExpenseReportScreen()
            }
        }
    }
}