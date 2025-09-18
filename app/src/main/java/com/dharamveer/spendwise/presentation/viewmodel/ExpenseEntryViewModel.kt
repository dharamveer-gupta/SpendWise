package com.dharamveer.spendwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharamveer.spendwise.data.local.entity.Expense
import com.dharamveer.spendwise.domain.usecase.ExpenseUseCases
import com.dharamveer.spendwise.presentation.state.ExpenseEntryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseEntryViewModel @Inject constructor(
    private val useCases: ExpenseUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseEntryState())
    val state: StateFlow<ExpenseEntryState> = _state

    fun onTitleChange(newTitle: String) = _state.update { it.copy(title = newTitle, errorTitle = if (newTitle.isBlank()) "Title cannot be empty" else null) }
    fun onAmountChange(newAmount: String) = _state.update {
        val amountVal = newAmount.toDoubleOrNull()
        it.copy(
            amount = newAmount,
            errorAmount = when {
                newAmount.isBlank() -> "Amount cannot be empty"
                amountVal == null || amountVal <= 0 -> "Enter valid amount"
                else -> null
            }
        )
    }
    fun onCategoryChange(newCategory: String) = _state.update { it.copy(category = newCategory, errorCategory = if (newCategory.isBlank()) "Please select a category" else null) }
    fun onNotesChange(newNotes: String) = _state.update { it.copy(notes = newNotes) }

    fun addExpense() {
        val current = _state.value
        if (current.title.isBlank() || current.amount.isBlank()) {
            _state.update { it.copy(error = "Title and Amount are required") }
            return
        }

        val expense = Expense(
            title = current.title,
            amount = current.amount.toDoubleOrNull() ?: 0.0,
            category = current.category.ifBlank { "Other" },
            notes = current.notes,
            receiptPath = current.receiptPath,
            timestamp = System.currentTimeMillis(),
            isSynced = false
        )

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCases.addExpense(expense)

            // Recalculate today's total
            val todayStart = getStartOfDay()
            val todayEnd = getEndOfDay()
            useCases.getExpensesBetween(todayStart, todayEnd).collect { list ->
                val total = list.sumOf { it.amount }
                _state.update { st -> st.copy(
                    success = true,
                    isLoading = false,
                    totalSpentToday = total
                ) }
            }
        }
    }

    private fun getStartOfDay(): Long {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return cal.timeInMillis
    }

    private fun getEndOfDay(): Long {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return cal.timeInMillis
    }
}
