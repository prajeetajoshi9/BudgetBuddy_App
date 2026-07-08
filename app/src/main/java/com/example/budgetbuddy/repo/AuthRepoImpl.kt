package com.example.budgetbuddy.repo

import com.example.budgetbuddy.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AuthRepoImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance()
        .getReference("users")
) : AuthRepo {

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    callback(true, "Account created successfully", uid)
                } else {
                    callback(false, task.exception?.message ?: "Signup failed", "")
                }
            }
    }

    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String, UserModel?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val uid = auth.currentUser?.uid

                    if (uid.isNullOrBlank()) {
                        auth.signOut()
                        callback(false, "Login failed", null)
                        return@addOnCompleteListener
                    }

                    dbRef.child(uid).addListenerForSingleValueEvent(
                        object : ValueEventListener {

                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {

                                    val user = snapshot.getValue(UserModel::class.java)

                                    when {
                                        user == null -> {
                                            callback(false, "User data not found", null)
                                        }

                                        user.blocked -> {
                                            auth.signOut()
                                            callback(false, "Your account is blocked by admin", null)
                                        }

                                        else -> {
                                            callback(true, "Login successful", user)
                                        }
                                    }

                                } else {
                                    auth.signOut()
                                    callback(false, "User data not found", null)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                auth.signOut()
                                callback(false, error.message, null)
                            }
                        }
                    )

                } else {
                    callback(false, task.exception?.message ?: "Login failed", null)
                }
            }
    }

    override fun addUser(
        uid: String,
        user: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        dbRef.child(uid).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "User added successfully")
                } else {
                    callback(false, task.exception?.message ?: "Failed to add user")
                }
            }
    }

    override fun rollbackCurrentUserRegistration() {
        auth.currentUser?.delete()
        auth.signOut()
    }
}