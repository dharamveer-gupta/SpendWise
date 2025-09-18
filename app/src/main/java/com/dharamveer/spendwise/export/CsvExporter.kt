package com.dharamveer.spendwise.export

import android.content.Context
import com.dharamveer.spendwise.data.local.entity.Expense
import java.io.File
import java.io.FileWriter

object CsvExporter {

    fun export(context: Context, expenses: List<Expense>): File {
        val file = File(context.getExternalFilesDir(null), "expenses.csv")
        FileWriter(file).use { writer ->
            writer.append("Title,Amount,Category,Notes,Date\n")
            expenses.forEach { e ->
                writer.append("${e.title},${e.amount},${e.category},${e.notes},${e.timestamp}\n")
            }
        }
        return file
    }
}
