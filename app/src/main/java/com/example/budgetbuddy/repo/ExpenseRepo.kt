package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.ExpenseModel

interface ExpenseRepo {

    fun addExpense(model: ExpenseModel, callback: (Boolean, String) -> Unit)

    fun getExpenseByUser(
        userId: String,
        callback: (Boolean, String, List<ExpenseModel>) -> Unit
    )

    fun getAllExpenses(
        callback: (Boolean, String, List<ExpenseModel>) -> Unit
    )

    fun deleteExpense(expenseId: String, callback: (Boolean, String) -> Unit)
}