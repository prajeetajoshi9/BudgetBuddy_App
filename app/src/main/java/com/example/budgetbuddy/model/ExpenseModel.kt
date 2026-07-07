package com.example.budgetbuddy.model

data class ExpenseModel(
    val expenseId: String = "",
    val userId: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val note: String = "",
    val date: String = "",
    val createdAt: Long = System.currentTimeMillis()
)