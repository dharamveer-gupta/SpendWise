package com.dharamveer.spendwise.presentation.state

import com.dharamveer.spendwise.data.local.entity.Expense

/**
 * UI state for the Expense List screen.
 */
data class ExpenseListState(
    val expenses: List<Expense> = emptyList(),
    val totalAmount: Double = 0.0,
    val totalCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val empty: Boolean = false
)
