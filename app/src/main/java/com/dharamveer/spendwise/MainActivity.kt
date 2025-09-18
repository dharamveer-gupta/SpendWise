package com.dharamveer.spendwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dharamveer.spendwise.presentation.navigation.SpendWiseNavHost
import com.dharamveer.spendwise.ui.theme.SpendWiseTheme
import com.dharamveer.spendwise.worker.SyncScheduler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Schedule periodic sync when the app starts
        SyncScheduler.scheduleExpenseSync(this)

        setContent {
            SpendWiseTheme {
                SpendWiseNavHost()
            }
        }
    }
}