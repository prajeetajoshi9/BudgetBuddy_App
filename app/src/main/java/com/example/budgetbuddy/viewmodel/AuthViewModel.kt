package com.example.budgetbuddy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.repo.AuthRepo
import com.example.budgetbuddy.repo.AuthRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    private val repo: AuthRepo = AuthRepoImpl()
) : ViewModel() {

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> = _user.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun clearMessage() {
        _message.value = null
    }

    fun login(
        email: String,
        password: String
    ) {
        if (email.isBlank() || password.isBlank()) {
            _message.value = "Please fill all fields"
            return
        }

        _loading.value = true

        repo.login(
            email = email.trim(),
            password = password
        ) { success, msg, userData ->

            _loading.value = false
            _message.value = msg

            if (success && userData != null) {
                _user.value = userData
            }
        }
    }

    fun registerUser(
        fullName: String,
        email: String,
        contact: String,
        address: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        if (
            fullName.isBlank() ||
            email.isBlank() ||
            contact.isBlank() ||
            address.isBlank() ||
            password.isBlank() ||
            confirmPassword.isBlank()
        ) {
            _message.value = "Please fill all fields"
            return
        }

        if (password != confirmPassword) {
            _message.value = "Password does not match"
            return
        }

        _loading.value = true

        repo.register(email, password) { success, message, uid ->

            if (success) {

                val user = UserModel(
                    userId = uid,
                    name = fullName,
                    email = email,
                    contact = contact,
                    address = address,
                    role = "user",
                    blocked = false
                )

                repo.addUser(uid, user) { addSuccess, addMessage ->

                    _loading.value = false

                    if (addSuccess) {
                        _message.value = "Signup Successful"
                        onSuccess()
                    } else {
                        repo.rollbackCurrentUserRegistration()
                        _message.value = addMessage
                    }
                }

            } else {
                _loading.value = false

                _message.value =
                    if (message.contains("already", ignoreCase = true)) {
                        "Email already in use."
                    } else {
                        message
                    }
            }
        }
    }
}