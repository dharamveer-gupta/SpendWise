package com.dharamveer.spendwise.export

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.dharamveer.spendwise.data.local.entity.Expense
import java.io.File
import java.io.FileOutputStream

object PdfExporter {

    fun export(context: Context, expenses: List<Expense>): File {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        val paint = android.graphics.Paint().apply { textSize = 14f }

        var y = 50
        canvas.drawText("Expense Report", 200f, y.toFloat(), paint)
        y += 40

        expenses.forEachIndexed { i, e ->
            val line = "${i + 1}. ${e.title} - ₹${e.amount} [${e.category}]"
            canvas.drawText(line, 40f, y.toFloat(), paint)
            y += 25
        }

        pdfDocument.finishPage(page)

        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "expenses.pdf"
        )
        FileOutputStream(file).use { out ->
            pdfDocument.writeTo(out)
        }
        pdfDocument.close()

        return file
    }
}
