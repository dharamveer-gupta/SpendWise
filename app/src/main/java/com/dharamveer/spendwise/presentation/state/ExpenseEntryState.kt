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
    val success: Boolean = false, // true after successful save
    val totalSpentToday: Double = 0.0,

    // ✅ Validation fields
    val errorTitle: String? = null,
    val errorAmount: String? = null,
    val errorCategory: String? = null
) {
    val isValid: Boolean
        get() = errorTitle == null &&
                errorAmount == null &&
                errorCategory == null &&
                title.isNotBlank() &&
                amount.isNotBlank() &&
                category.isNotBlank()
}


