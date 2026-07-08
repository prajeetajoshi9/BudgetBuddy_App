package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.NotificationModel
import com.google.firebase.database.FirebaseDatabase

class NotificationRepoImpl : NotificationRepo {

    private val database = FirebaseDatabase.getInstance()
    private val notificationRef = database.reference.child("notifications")

    override fun addNotification(
        model: NotificationModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id = notificationRef.push().key ?: ""

        val newNotification = model.copy(
            notificationId = id
        )

        notificationRef.child(id)
            .setValue(newNotification)
            .addOnSuccessListener {
                callback(true, "Notification saved")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to save notification")
            }
    }

    override fun getNotificationByUser(
        userId: String,
        callback: (Boolean, String, List<NotificationModel>) -> Unit
    ) {
        notificationRef.get()
            .addOnSuccessListener { snapshot ->

                val list = mutableListOf<NotificationModel>()

                for (data in snapshot.children) {
                    val notification = data.getValue(NotificationModel::class.java)

                    if (notification != null && notification.target == userId) {
                        list.add(notification)
                    }
                }

                callback(true, "Notifications loaded", list)
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to load notifications", emptyList())
            }
    }

    override fun deleteNotification(
        notificationId: String,
        callback: (Boolean, String) -> Unit
    ) {
        notificationRef.child(notificationId)
            .removeValue()
            .addOnSuccessListener {
                callback(true, "Notification deleted")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to delete notification")
            }
    }
}