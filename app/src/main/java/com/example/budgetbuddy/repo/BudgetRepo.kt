package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.BudgetModel

interface BudgetRepo {

    fun addBudget(
        model: BudgetModel,
        callback: (Boolean, String) -> Unit
    )

    fun getBudgetByUser(
        userId: String,
        callback: (Boolean, String, List<BudgetModel>) -> Unit
    )

    fun getAllBudgets(
        callback: (Boolean, String, List<BudgetModel>) -> Unit
    )

    fun deleteBudget(
        budgetId: String,
        callback: (Boolean, String) -> Unit
    )
}