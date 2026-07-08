package com.example.budgetbuddy.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfHelper {

    fun createReportPdf(
        context: Context,
        totalBudget: Double,
        totalExpense: Double,
        saving: Double,
        categoryTotals: Map<String, Double>
    ): File? {

        return try {
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdfDocument.startPage(pageInfo)

            val canvas = page.canvas
            val paint = Paint()

            val date = SimpleDateFormat(
                "MMM dd, yyyy hh:mm a",
                Locale.getDefault()
            ).format(Date())

            paint.textSize = 26f
            paint.isFakeBoldText = true
            canvas.drawText("BudgetBuddy Report", 40f, 60f, paint)

            paint.textSize = 13f
            paint.isFakeBoldText = false
            canvas.drawText("Generated on: $date", 40f, 85f, paint)

            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("Summary", 40f, 130f, paint)

            paint.textSize = 15f
            paint.isFakeBoldText = false
            canvas.drawText("Total Budget: Rs. $totalBudget", 40f, 165f, paint)
            canvas.drawText("Total Expense: Rs. $totalExpense", 40f, 195f, paint)
            canvas.drawText("Remaining: Rs. $saving", 40f, 225f, paint)

            paint.textSize = 18f
            paint.isFakeBoldText = true
            canvas.drawText("Category Report", 40f, 280f, paint)

            paint.textSize = 15f
            paint.isFakeBoldText = false

            var y = 315f

            if (categoryTotals.isEmpty()) {
                canvas.drawText("No expense category data available.", 40f, y, paint)
            } else {
                categoryTotals.forEach { item ->
                    canvas.drawText(
                        "${item.key.ifEmpty { "Other" }}: Rs. ${item.value}",
                        40f,
                        y,
                        paint
                    )
                    y += 30f
                }
            }


            pdfDocument.finishPage(page)

            val folder = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                ?: context.filesDir

            val file = File(
                folder,
                "BudgetBuddy_Report_${System.currentTimeMillis()}.pdf"
            )

            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            file

        } catch (e: Exception) {
            null
        }
    }
}