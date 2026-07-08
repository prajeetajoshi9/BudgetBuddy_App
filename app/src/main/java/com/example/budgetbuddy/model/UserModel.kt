package com.example.budgetbuddy.model

data class UserModel(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val contact: String = "",
    val address: String = "",
    val role: String = "user",
    val blocked: Boolean = false
)