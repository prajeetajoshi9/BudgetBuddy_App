package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.repo.UserRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserRepoImpl : UserRepo {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.reference.child("users")

    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userId = auth.currentUser?.uid ?: ""

                userRef.child(userId).get()
                    .addOnSuccessListener { snapshot ->
                        val user = snapshot.getValue(UserModel::class.java)

                        if (user?.blocked == true) {
                            auth.signOut()
                            callback(false, "Your account is blocked by admin", "")
                        } else {
                            callback(true, "Login successful", user?.role ?: "user")                        }
                    }
                    .addOnFailureListener {
                        callback(false, it.message ?: "Failed to get user data", "")
                    }
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Login failed", "")
            }
    }

    override fun register(
        email: String,
        password: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userId = auth.currentUser?.uid ?: ""

                val user = model.copy(
                    userId = userId,
                    email = email
                )

                userRef.child(userId).setValue(user)
                    .addOnSuccessListener {
                        callback(true, "Registration successful")
                    }
                    .addOnFailureListener {
                        callback(false, it.message ?: "Failed to save user")
                    }
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Registration failed")
            }
    }

    override fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                callback(true, "Password reset link sent to your email")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to send reset link")
            }
    }

    override fun logout(callback: (Boolean, String) -> Unit) {
        auth.signOut()
        callback(true, "Logout successful")
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    override fun getUserById(
        userId: String,
        callback: (Boolean, String, UserModel?) -> Unit
    ) {
        userRef.child(userId).get()
            .addOnSuccessListener { snapshot ->
                val user = snapshot.getValue(UserModel::class.java)
                callback(true, "User fetched", user)
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to fetch user", null)
            }
    }

    override fun updateProfile(
        userId: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        userRef.child(userId).setValue(model)
            .addOnSuccessListener {
                callback(true, "Profile updated")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Profile update failed")
            }
    }

    override fun getAllUsers(
        callback: (Boolean, String, List<UserModel>) -> Unit
    ) {
        userRef.get()
            .addOnSuccessListener { snapshot ->
                val users = mutableListOf<UserModel>()

                for (child in snapshot.children) {
                    val user = child.getValue(UserModel::class.java)
                    if (user != null) {
                        users.add(user)
                    }
                }

                callback(true, "Users fetched", users)
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to fetch users", emptyList())
            }
    }

    override fun blockUser(
        userId: String,
        blocked: Boolean,
        callback: (Boolean, String) -> Unit
    ) {
        userRef.child(userId).child("blocked").setValue(blocked)
            .addOnSuccessListener {
                callback(true, if (blocked) "User blocked" else "User unblocked")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to update user status")
            }
    }

    override fun deleteUser(
        userId: String,
        callback: (Boolean, String) -> Unit
    ) {
        userRef.child(userId).removeValue()
            .addOnSuccessListener {
                callback(true, "User deleted")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to delete user")
            }
    }
}