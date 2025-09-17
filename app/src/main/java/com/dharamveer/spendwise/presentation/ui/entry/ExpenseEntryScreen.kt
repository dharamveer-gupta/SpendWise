package com.dharamveer.spendwise.presentation.ui.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dharamveer.spendwise.presentation.viewmodel.ExpenseEntryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEntryScreen(viewModel: ExpenseEntryViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Add Expense")
            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Total Spent Today
            Text(
                text = "Total Spent Today: ₹${state.totalSpentToday}",
                style = MaterialTheme.typography.titleMedium
            )

            // Title
            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.onTitleChange(it) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            // Amount
            OutlinedTextField(
                value = state.amount,
                onValueChange = { viewModel.onAmountChange(it) },
                label = { Text("Amount (₹)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Category
            OutlinedTextField(
                value = state.category,
                onValueChange = { viewModel.onCategoryChange(it) },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            // Notes
            OutlinedTextField(
                value = state.notes,
                onValueChange = { viewModel.onNotesChange(it) },
                label = { Text("Notes (optional)") },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Submit Button
            Button(
                onClick = { viewModel.addExpense() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save Expense")
            }

            // Error / Success messages
            state.error?.let { err ->
                Text(text = err, color = MaterialTheme.colorScheme.error)
            }
            if (state.success) {
                Text(text = "Expense saved successfully!", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}