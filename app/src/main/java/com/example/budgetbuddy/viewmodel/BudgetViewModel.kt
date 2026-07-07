package com.example.budgetbuddy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.model.BudgetModel
import com.example.budgetbuddy.repo.BudgetRepo
import com.example.budgetbuddy.repo.BudgetRepoImpl

class BudgetViewModel : ViewModel() {

    private val repo: BudgetRepo = BudgetRepoImpl()

    fun addBudget(
        model: BudgetModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addBudget(model, callback)
    }

    fun updateBudget(
        budgetId: String,
        model: BudgetModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.updateBudget(budgetId, model, callback)
    }

    fun deleteBudget(
        budgetId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteBudget(budgetId, callback)
    }

    fun getBudgetByUser(
        userId: String,
        callback: (Boolean, String, List<BudgetModel>) -> Unit
    ) {
        repo.getBudgetByUser(userId, callback)
    }

    fun getBudgetById(
        budgetId: String,
        callback: (Boolean, String, BudgetModel?) -> Unit
    ) {
        repo.getBudgetById(budgetId, callback)
    }
}