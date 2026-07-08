package com.example.budgetbuddy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.model.ExpenseModel
import com.example.budgetbuddy.repo.ExpenseRepo
import com.example.budgetbuddy.repo.ExpenseRepoImpl

class ExpenseViewModel : ViewModel() {

    private val repo: ExpenseRepo = ExpenseRepoImpl()

    fun addExpense(
        model: ExpenseModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addExpense(model, callback)
    }

    fun getExpenseByUser(
        userId: String,
        callback: (Boolean, String, List<ExpenseModel>) -> Unit
    ) {
        repo.getExpenseByUser(userId, callback)
    }

    fun deleteExpense(
        expenseId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteExpense(expenseId, callback)
    }
    fun getAllExpenses(
        callback: (Boolean, String, List<ExpenseModel>) -> Unit
    ) {
        repo.getAllExpenses(callback)
    }
}