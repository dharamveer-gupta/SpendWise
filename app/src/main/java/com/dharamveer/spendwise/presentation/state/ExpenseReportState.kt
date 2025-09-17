package com.dharamveer.spendwise.presentation.state

/**
 * UI state for the Expense Report screen (7-day summary).
 */
data class ExpenseReportState(
    val dailyTotals: Map<String, Double> = emptyMap(),   // e.g. {"2025-09-17" to 200.0}
    val categoryTotals: Map<String, Double> = emptyMap(), // e.g. {"Food" to 500.0}
    val isLoading: Boolean = false,
    val error: String? = null
)
