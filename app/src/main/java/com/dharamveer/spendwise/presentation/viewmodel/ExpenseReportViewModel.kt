package com.dharamveer.spendwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharamveer.spendwise.domain.usecase.ExpenseUseCases
import com.dharamveer.spendwise.presentation.state.ExpenseReportState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseReportViewModel @Inject constructor(
    private val useCases: ExpenseUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseReportState())
    val state: StateFlow<ExpenseReportState> = _state

    init {
        loadWeeklyReport()
    }

    private fun loadWeeklyReport() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            useCases.getAllExpenses().collectLatest { expenses ->
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dailyTotals = mutableMapOf<String, Double>()
                val categoryTotals = mutableMapOf<String, Double>()

                // compute last 7 days
                val cal = Calendar.getInstance()
                val todayEnd = cal.timeInMillis

                for (i in 0..6) {
                    val dayCal = Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, -i)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    val dayStart = dayCal.timeInMillis
                    val dayEnd = dayStart + (24 * 60 * 60 * 1000) - 1

                    val dayExpenses = expenses.filter { it.timestamp in dayStart..dayEnd }
                    dailyTotals[sdf.format(Date(dayStart))] = dayExpenses.sumOf { it.amount }
                }

                // category totals
                expenses.forEach { e ->
                    val old = categoryTotals.getOrDefault(e.category, 0.0)
                    categoryTotals[e.category] = old + e.amount
                }

                _state.update {
                    it.copy(
                        dailyTotals = dailyTotals,
                        categoryTotals = categoryTotals,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }
}
