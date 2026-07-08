package com.example.budgetbuddy.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SplashBody()
        }
    }
}

@Composable
fun SplashBody() {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(1500)

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null) {
            context.startActivity(
                Intent(context, LoginActivity::class.java)
            )
        } else {
            FirebaseDatabase.getInstance()
                .reference
                .child("users")
                .child(currentUser.uid)
                .get()
                .addOnSuccessListener { snapshot ->

                    val role = snapshot.child("role").value.toString()

                    if (role == "admin") {
                        context.startActivity(
                            Intent(context, AdminDashboardActivity::class.java)
                        )
                    } else {
                        context.startActivity(
                            Intent(context, UserDashboardActivity::class.java)
                        )
                    }
                }
                .addOnFailureListener {
                    context.startActivity(
                        Intent(context, LoginActivity::class.java)
                    )
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .height(400.dp)
                .width(400.dp)
        )

        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashBody()
}