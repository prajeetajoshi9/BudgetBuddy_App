package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.NotificationModel

interface NotificationRepo {

    fun addNotification(
        model: NotificationModel,
        callback: (Boolean, String) -> Unit
    )

    fun getNotificationByUser(
        userId: String,
        callback: (Boolean, String, List<NotificationModel>) -> Unit
    )

    fun deleteNotification(
        notificationId: String,
        callback: (Boolean, String) -> Unit
    )
}