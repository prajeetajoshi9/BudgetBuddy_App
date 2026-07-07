package com.example.budgetbuddy.model

data class BudgetModel(
    val budgetId: String = "",
    val userId: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val month: String = "",
    val createdAt: Long = System.currentTimeMillis()
)