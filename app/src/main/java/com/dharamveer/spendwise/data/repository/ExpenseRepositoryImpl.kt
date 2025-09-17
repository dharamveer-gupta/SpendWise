package com.dharamveer.spendwise.data.repository

import com.dharamveer.spendwise.data.local.dao.ExpenseDao
import com.dharamveer.spendwise.data.local.entity.Expense
import com.dharamveer.spendwise.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun addExpense(expense: Expense) {
        expenseDao.insert(expense)
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.update(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.delete(expense)
    }

    override fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses()
    }

    override fun getExpensesBetween(startTs: Long, endTs: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesBetween(startTs, endTs)
    }

    override suspend fun getUnsyncedExpenses(): List<Expense> {
        return expenseDao.getUnsyncedExpenses()
    }

    override suspend fun markExpensesSynced(ids: List<Int>) {
        expenseDao.markExpensesSynced(ids)
    }
}
