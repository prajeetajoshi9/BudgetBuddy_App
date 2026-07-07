package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.BudgetModel

interface BudgetRepo {

    fun addBudget(
        model: BudgetModel,
        callback: (Boolean, String) -> Unit
    )

    fun updateBudget(
        budgetId: String,
        model: BudgetModel,
        callback: (Boolean, String) -> Unit
    )

    fun deleteBudget(
        budgetId: String,
        callback: (Boolean, String) -> Unit
    )

    fun getBudgetByUser(
        userId: String,
        callback: (Boolean, String, List<BudgetModel>) -> Unit
    )

    fun getBudgetById(
        budgetId: String,
        callback: (Boolean, String, BudgetModel?) -> Unit
    )
}