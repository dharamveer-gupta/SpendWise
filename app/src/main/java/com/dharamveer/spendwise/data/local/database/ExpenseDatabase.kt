package com.dharamveer.spendwise.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dharamveer.spendwise.data.local.dao.ExpenseDao
import com.dharamveer.spendwise.data.local.entity.Expense

@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
