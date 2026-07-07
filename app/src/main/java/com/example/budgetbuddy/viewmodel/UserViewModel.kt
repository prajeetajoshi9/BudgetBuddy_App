package com.example.budgetbuddy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.repo.UserRepo
import com.example.budgetbuddy.repo.UserRepoImpl

class UserViewModel : ViewModel() {

    private val repo: UserRepo = UserRepoImpl()

    fun login(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        repo.login(email, password, callback)
    }

    fun register(
        email: String,
        password: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.register(email, password, model, callback)
    }

    fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.forgetPassword(email, callback)
    }

    fun logout(callback: (Boolean, String) -> Unit) {
        repo.logout(callback)
    }

    fun getCurrentUserId(): String? {
        return repo.getCurrentUserId()
    }

    fun getUserById(
        userId: String,
        callback: (Boolean, String, UserModel?) -> Unit
    ) {
        repo.getUserById(userId, callback)
    }

    fun updateProfile(
        userId: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.updateProfile(userId, model, callback)
    }

    fun getAllUsers(
        callback: (Boolean, String, List<UserModel>) -> Unit
    ) {
        repo.getAllUsers(callback)
    }

    fun blockUser(
        userId: String,
        blocked: Boolean,
        callback: (Boolean, String) -> Unit
    ) {
        repo.blockUser(userId, blocked, callback)
    }

    fun deleteUser(
        userId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteUser(userId, callback)
    }
}