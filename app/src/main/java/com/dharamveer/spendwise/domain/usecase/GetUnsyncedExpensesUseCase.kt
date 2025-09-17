package com.dharamveer.spendwise.domain.usecase

import com.dharamveer.spendwise.domain.repository.ExpenseRepository
import javax.inject.Inject

class GetUnsyncedExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke() = repository.getUnsyncedExpenses()
}