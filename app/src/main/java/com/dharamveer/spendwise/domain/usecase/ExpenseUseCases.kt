package com.dharamveer.spendwise.domain.usecase

import javax.inject.Inject

data class ExpenseUseCases @Inject constructor(
    val addExpense: AddExpenseUseCase,
    val getAllExpenses: GetAllExpensesUseCase,
    val getExpensesBetween: GetExpensesBetweenUseCase,
    val updateExpense: UpdateExpenseUseCases,
    val deleteExpense: DeleteExpenseUseCases,
    val getUnsyncedExpenses: GetUnsyncedExpensesUseCase,
    val markExpensesSynced: MarkExpensesSyncedUseCase,
)
