package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.UserModel

interface UserRepo {

    fun login(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    )

    fun register(
        email: String,
        password: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    )

    fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    )

    fun logout(callback: (Boolean, String) -> Unit)

    fun getCurrentUserId(): String?

    fun getUserById(
        userId: String,
        callback: (Boolean, String, UserModel?) -> Unit
    )

    fun getAllUsers(
        callback: (Boolean, String, List<UserModel>) -> Unit
    )

    fun blockUser(
        userId: String,
        blocked: Boolean,
        callback: (Boolean, String) -> Unit
    )

    fun deleteUser(
        userId: String,
        callback: (Boolean, String) -> Unit
    )

    fun updateProfile(
        userId: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    )
}