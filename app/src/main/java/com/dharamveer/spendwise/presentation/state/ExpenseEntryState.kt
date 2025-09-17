package com.dharamveer.spendwise.presentation.state


/**
 * UI state for the Expense Entry screen.
 */
data class ExpenseEntryState(
    val title: String = "",
    val amount: String = "",
    val category: String = "",
    val notes: String = "",
    val receiptPath: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false, // set true after successful save
    val totalSpentToday: Double = 0.0
)

