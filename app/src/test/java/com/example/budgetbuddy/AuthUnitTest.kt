package com.example.budgetbuddy

import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.repo.AuthRepoImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class AuthUnitTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    @Mock
    private lateinit var mockFirebaseUser: FirebaseUser

    @Mock
    private lateinit var mockDbRef: DatabaseReference

    @Mock
    private lateinit var mockChildRef: DatabaseReference

    @Mock
    private lateinit var mockSnapshot: DataSnapshot

    private lateinit var authRepo: AuthRepoImpl

    @Captor
    private lateinit var authCaptor: ArgumentCaptor<OnCompleteListener<AuthResult>>

    @Captor
    private lateinit var dbCaptor: ArgumentCaptor<ValueEventListener>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        authRepo = AuthRepoImpl(mockAuth, mockDbRef)
    }

    @Test
    fun testLogin_Successful() {

        val email = "test@example.com"
        val password = "testPassword"
        val uid = "testUid"

        var actualSuccess = false
        var actualMessage = ""
        var actualUser: UserModel? = null

        val userModel = UserModel(
            userId = uid,
            name = "Test User",
            email = email,
            contact = "9800000000",
            address = "Kathmandu",
            role = "user",
            blocked = false
        )

        `when`(mockAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(mockTask)

        `when`(mockTask.isSuccessful)
            .thenReturn(true)

        `when`(mockAuth.currentUser)
            .thenReturn(mockFirebaseUser)

        `when`(mockFirebaseUser.uid)
            .thenReturn(uid)

        `when`(mockDbRef.child(uid))
            .thenReturn(mockChildRef)

        `when`(mockSnapshot.exists())
            .thenReturn(true)

        `when`(mockSnapshot.getValue(UserModel::class.java))
            .thenReturn(userModel)

        authRepo.login(email, password) { success, message, user ->
            actualSuccess = success
            actualMessage = message
            actualUser = user
        }

        verify(mockTask).addOnCompleteListener(authCaptor.capture())
        authCaptor.value.onComplete(mockTask)

        verify(mockChildRef).addListenerForSingleValueEvent(dbCaptor.capture())
        dbCaptor.value.onDataChange(mockSnapshot)

        assertEquals(true, actualSuccess)
        assertEquals("Login successful", actualMessage)
        assertEquals(userModel, actualUser)
    }

    @Test
    fun testLogin_BlockedUser() {

        val email = "blocked@example.com"
        val password = "testPassword"
        val uid = "blockedUid"

        var actualSuccess = true
        var actualMessage = ""

        val userModel = UserModel(
            userId = uid,
            name = "Blocked User",
            email = email,
            contact = "9800000000",
            address = "Kathmandu",
            role = "user",
            blocked = true
        )

        `when`(mockAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(mockTask)

        `when`(mockTask.isSuccessful)
            .thenReturn(true)

        `when`(mockAuth.currentUser)
            .thenReturn(mockFirebaseUser)

        `when`(mockFirebaseUser.uid)
            .thenReturn(uid)

        `when`(mockDbRef.child(uid))
            .thenReturn(mockChildRef)

        `when`(mockSnapshot.exists())
            .thenReturn(true)

        `when`(mockSnapshot.getValue(UserModel::class.java))
            .thenReturn(userModel)

        authRepo.login(email, password) { success, message, _ ->
            actualSuccess = success
            actualMessage = message
        }

        verify(mockTask).addOnCompleteListener(authCaptor.capture())
        authCaptor.value.onComplete(mockTask)

        verify(mockChildRef).addListenerForSingleValueEvent(dbCaptor.capture())
        dbCaptor.value.onDataChange(mockSnapshot)

        assertEquals(false, actualSuccess)
        assertEquals("Your account is blocked by admin", actualMessage)
    }

    @Test
    fun testRegister_Successful() {

        val email = "test@example.com"
        val password = "password123"
        val uid = "newUid"

        var actualSuccess = false
        var actualMessage = ""
        var actualUid = ""

        `when`(mockAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(mockTask)

        `when`(mockTask.isSuccessful)
            .thenReturn(true)

        `when`(mockAuth.currentUser)
            .thenReturn(mockFirebaseUser)

        `when`(mockFirebaseUser.uid)
            .thenReturn(uid)

        authRepo.register(email, password) { success, message, returnedUid ->
            actualSuccess = success
            actualMessage = message
            actualUid = returnedUid
        }

        verify(mockTask).addOnCompleteListener(authCaptor.capture())
        authCaptor.value.onComplete(mockTask)

        assertEquals(true, actualSuccess)
        assertEquals("Account created successfully", actualMessage)
        assertEquals(uid, actualUid)
    }
}