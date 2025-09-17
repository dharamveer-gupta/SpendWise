package com.dharamveer.spendwise.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * Room entity representing an expense record.
 * timestamp is epoch millis.
 */
@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val amount: Double,

    val category: String, // e.g. "Staff", "Travel", "Food", "Utility"

    val notes: String? = null,

    @ColumnInfo(name = "receipt_path")
    val receiptPath: String? = null, // local file uri/path or null

    val timestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false
)
