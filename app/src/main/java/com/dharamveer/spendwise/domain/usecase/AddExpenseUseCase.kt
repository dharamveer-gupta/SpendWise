package com.dharamveer.spendwise.domain.usecase

import com.dharamveer.spendwise.data.local.entity.Expense
import com.dharamveer.spendwise.domain.repository.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) = repository.addExpense(expense)

}
