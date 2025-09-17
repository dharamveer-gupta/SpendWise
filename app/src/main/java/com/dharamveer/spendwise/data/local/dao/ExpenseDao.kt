package com.dharamveer.spendwise.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.dharamveer.spendwise.data.local.entity.Expense

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense): Long

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    /** All expenses (descending by timestamp) as a Flow */
    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    /**
     * Expenses between two epoch-ms timestamps inclusive.
     * We'll use this for "today" or date range queries.
     */
    @Query("SELECT * FROM expenses WHERE timestamp BETWEEN :startTs AND :endTs ORDER BY timestamp DESC")
    fun getExpensesBetween(startTs: Long, endTs: Long): Flow<List<Expense>>

    /** Unsynced expenses (used by the WorkManager sync mock) */
    @Query("SELECT * FROM expenses WHERE is_synced = 0 ORDER BY timestamp ASC")
    suspend fun getUnsyncedExpenses(): List<Expense>

    /** Mark given expense ids as synced */
    @Query("UPDATE expenses SET is_synced = 1 WHERE id IN (:ids)")
    suspend fun markExpensesSynced(ids: List<Int>): Int
}
