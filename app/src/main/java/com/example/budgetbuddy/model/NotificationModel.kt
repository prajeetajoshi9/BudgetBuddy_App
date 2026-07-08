package com.example.budgetbuddy.model

data class NotificationModel(

    val notificationId: String = "",
    val title: String = "",
    val message: String = "",
    val date: String = "",
    val target: String = "",
    val read: Boolean = false
)