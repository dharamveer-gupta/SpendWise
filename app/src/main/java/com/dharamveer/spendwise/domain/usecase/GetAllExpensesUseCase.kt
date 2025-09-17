package com.dharamveer.spendwise.domain.usecase

import com.dharamveer.spendwise.domain.repository.ExpenseRepository
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke()= repository.getAllExpenses()
}