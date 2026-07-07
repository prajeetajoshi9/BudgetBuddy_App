package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.ExpenseModel
import com.google.firebase.database.FirebaseDatabase

class ExpenseRepoImpl : ExpenseRepo {

    private val expenseRef = FirebaseDatabase.getInstance()
        .reference
        .child("expenses")

    override fun addExpense(
        model: ExpenseModel,
        callback: (Boolean, String) -> Unit
    ) {

        val expenseId = expenseRef.push().key ?: ""

        val expense = model.copy(
            expenseId = expenseId
        )

        expenseRef.child(expenseId)
            .setValue(expense)
            .addOnSuccessListener {
                callback(true, "Expense added successfully")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to add expense")
            }
    }

    override fun getExpenseByUser(
        userId: String,
        callback: (Boolean, String, List<ExpenseModel>) -> Unit
    ) {

        expenseRef.get()
            .addOnSuccessListener { snapshot ->

                val expenses = mutableListOf<ExpenseModel>()

                for (child in snapshot.children) {

                    val expense =
                        child.getValue(ExpenseModel::class.java)

                    if (expense != null && expense.userId == userId) {
                        expenses.add(expense)
                    }
                }

                callback(
                    true,
                    "Expenses fetched successfully",
                    expenses
                )
            }
            .addOnFailureListener {

                callback(
                    false,
                    it.message ?: "Failed to fetch expenses",
                    emptyList()
                )
            }
    }

    override fun deleteExpense(
        expenseId: String,
        callback: (Boolean, String) -> Unit
    ) {

        expenseRef.child(expenseId)
            .removeValue()
            .addOnSuccessListener {
                callback(true, "Expense deleted successfully")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to delete expense")
            }
    }
}