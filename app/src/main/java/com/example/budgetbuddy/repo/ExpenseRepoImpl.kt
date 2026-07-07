package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.ExpenseModel
import com.example.budgetbuddy.repo.ExpenseRepo
import com.google.firebase.database.FirebaseDatabase

class ExpenseRepoImpl : ExpenseRepo {

    private val database = FirebaseDatabase.getInstance()
    private val expenseRef = database.reference.child("expenses")

    override fun addExpense(
        model: ExpenseModel,
        callback: (Boolean, String) -> Unit
    ) {
        val expenseId = expenseRef.push().key ?: ""

        val expense = model.copy(
            expenseId = expenseId
        )

        expenseRef.child(expenseId).setValue(expense)
            .addOnSuccessListener {
                callback(true, "Expense added successfully")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to add expense")
            }
    }

    override fun updateExpense(
        expenseId: String,
        model: ExpenseModel,
        callback: (Boolean, String) -> Unit
    ) {
        expenseRef.child(expenseId).setValue(model)
            .addOnSuccessListener {
                callback(true, "Expense updated successfully")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to update expense")
            }
    }

    override fun deleteExpense(
        expenseId: String,
        callback: (Boolean, String) -> Unit
    ) {
        expenseRef.child(expenseId).removeValue()
            .addOnSuccessListener {
                callback(true, "Expense deleted successfully")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to delete expense")
            }
    }

    override fun getExpenseByUser(
        userId: String,
        callback: (Boolean, String, List<ExpenseModel>) -> Unit
    ) {
        expenseRef.orderByChild("userId").equalTo(userId).get()
            .addOnSuccessListener { snapshot ->

                val expenseList = mutableListOf<ExpenseModel>()

                for (child in snapshot.children) {
                    val expense = child.getValue(ExpenseModel::class.java)

                    if (expense != null) {
                        expenseList.add(expense)
                    }
                }

                callback(
                    true,
                    "Expenses fetched successfully",
                    expenseList
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

    override fun getExpenseById(
        expenseId: String,
        callback: (Boolean, String, ExpenseModel?) -> Unit
    ) {
        expenseRef.child(expenseId).get()
            .addOnSuccessListener { snapshot ->

                val expense = snapshot.getValue(ExpenseModel::class.java)

                callback(
                    true,
                    "Expense fetched successfully",
                    expense
                )
            }
            .addOnFailureListener {
                callback(
                    false,
                    it.message ?: "Failed to fetch expense",
                    null
                )
            }
    }
}