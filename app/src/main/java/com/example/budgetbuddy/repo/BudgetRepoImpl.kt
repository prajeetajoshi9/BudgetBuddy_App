package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.BudgetModel
import com.google.firebase.database.FirebaseDatabase

class BudgetRepoImpl : BudgetRepo {

    private val budgetRef = FirebaseDatabase.getInstance()
        .reference
        .child("budgets")

    override fun addBudget(
        model: BudgetModel,
        callback: (Boolean, String) -> Unit
    ) {
        val budgetId = budgetRef.push().key ?: ""

        val budget = model.copy(budgetId = budgetId)

        budgetRef.child(budgetId).setValue(budget)
            .addOnSuccessListener {
                callback(true, "Budget added successfully")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to add budget")
            }
    }

    override fun getBudgetByUser(
        userId: String,
        callback: (Boolean, String, List<BudgetModel>) -> Unit
    ) {
        budgetRef.get()
            .addOnSuccessListener { snapshot ->

                val budgets = mutableListOf<BudgetModel>()

                for (child in snapshot.children) {
                    val budget = child.getValue(BudgetModel::class.java)

                    if (budget != null && budget.userId == userId) {
                        budgets.add(budget)
                    }
                }

                callback(true, "Budgets fetched successfully", budgets)
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to fetch budgets", emptyList())
            }
    }

    override fun deleteBudget(
        budgetId: String,
        callback: (Boolean, String) -> Unit
    ) {
        budgetRef.child(budgetId).removeValue()
            .addOnSuccessListener {
                callback(true, "Budget deleted successfully")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to delete budget")
            }
    }

    override fun getAllBudgets(
        callback: (Boolean, String, List<BudgetModel>) -> Unit
    ) {

        budgetRef.get()
            .addOnSuccessListener { snapshot ->

                val budgets = mutableListOf<BudgetModel>()

                for (child in snapshot.children) {
                    val budget =
                        child.getValue(BudgetModel::class.java)

                    if (budget != null) {
                        budgets.add(budget)
                    }
                }

                callback(
                    true,
                    "Budgets fetched successfully",
                    budgets
                )
            }
            .addOnFailureListener {

                callback(
                    false, it.message ?: "Failed to fetch budgets",
                    emptyList()
                )
            }
    }
    }
