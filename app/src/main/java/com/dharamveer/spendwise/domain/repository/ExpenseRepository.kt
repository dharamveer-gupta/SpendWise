package com.dharamveer.spendwise.domain.repository

import com.dharamveer.spendwise.data.local.entity.Expense
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction for expense data operations.
 * Implemented by data layer (Room-backed).
 */
interface ExpenseRepository {

    suspend fun addExpense(expense: Expense)

    suspend fun updateExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    fun getAllExpenses(): Flow<List<Expense>>

    fun getExpensesBetween(startTs: Long, endTs: Long): Flow<List<Expense>>

    suspend fun getUnsyncedExpenses(): List<Expense>

    suspend fun markExpensesSynced(ids: List<Int>)
}
