package com.dharamveer.spendwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharamveer.spendwise.domain.usecase.ExpenseUseCases
import com.dharamveer.spendwise.presentation.state.ExpenseListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val useCases: ExpenseUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseListState())
    val state: StateFlow<ExpenseListState> = _state

    init {
        loadAllExpenses()
    }

    private fun loadAllExpenses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            useCases.getAllExpenses().collectLatest { expenses ->
                _state.update {
                    it.copy(
                        expenses = expenses,
                        totalAmount = expenses.sumOf { e -> e.amount },
                        totalCount = expenses.size,
                        isLoading = false,
                        empty = expenses.isEmpty()
                    )
                }
            }
        }
    }

    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch {
            val currentList = _state.value.expenses
            val expense = currentList.find { it.id == expenseId }
            if (expense != null) {
                useCases.deleteExpense(expense)
            }
        }
    }
}
