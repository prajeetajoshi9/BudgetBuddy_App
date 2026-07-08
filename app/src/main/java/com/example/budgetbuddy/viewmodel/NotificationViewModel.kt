package com.example.budgetbuddy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.model.NotificationModel
import com.example.budgetbuddy.repo.NotificationRepoImpl

class NotificationViewModel : ViewModel() {

    private val repo = NotificationRepoImpl()

    fun addNotification(
        model: NotificationModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addNotification(model, callback)
    }

    fun getNotificationByUser(
        userId: String,
        callback: (Boolean, String, List<NotificationModel>) -> Unit
    ) {
        repo.getNotificationByUser(userId, callback)
    }

    fun deleteNotification(
        notificationId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteNotification(notificationId, callback)
    }
}