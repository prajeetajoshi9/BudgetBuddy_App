package com.example.budgetbuddy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LoginBody()
        }
    }
}

@Composable
fun LoginBody() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as? Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFF))
            .padding(horizontal = 25.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "Sign In",
            style = TextStyle(
                color = Color(0xFF4A6CF7),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Welcome Back",
            style = TextStyle(
                color = Color(0xFF7A7A7A),
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),

            placeholder = {
                Text("Enter Email")
            },

            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEAF0FF),
                unfocusedContainerColor = Color(0xFFEAF0FF),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },

            visualTransformation =
                if (visibility)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

            trailingIcon = {

                IconButton(
                    onClick = {
                        visibility = !visibility
                    }
                ) {

                    Icon(
                        painter =
                            if (visibility)
                                painterResource(R.drawable.baseline_visibility_24)
                            else
                                painterResource(R.drawable.baseline_visibility_off_24),

                        contentDescription = null,
                        tint = Color(0xFF4A6CF7)
                    )
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),

            placeholder = {
                Text("Enter Password")
            },

            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEAF0FF),
                unfocusedContainerColor = Color(0xFFEAF0FF),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Forget Password?",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(
                        context,
                        ForgetPasswordActivity::class.java
                    )

                    context.startActivity(intent)
                },
            color = Color(0xFF4A6CF7),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = {

                val sharedPreferences =
                    context.getSharedPreferences(
                        "User",
                        Context.MODE_PRIVATE
                    )

                val emailStorage =
                    sharedPreferences.getString(
                        "email",
                        ""
                    )

                val passwordStorage =
                    sharedPreferences.getString(
                        "password",
                        ""
                    )

                if (email == emailStorage &&
                    password == passwordStorage
                ) {

                    Toast.makeText(
                        context,
                        "Login Success",
                        Toast.LENGTH_LONG
                    ).show()

                    val intent = Intent(
                        context,
                        DashboardActivity::class.java
                    )

                    context.startActivity(intent)

                    activity?.finish()

                } else {

                    Toast.makeText(
                        context,
                        "Login Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }

            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape = RoundedCornerShape(15.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A6CF7)
            )

        ) {

            Text(
                text = "Login",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {

            Text(
                text = "Don't have an account?",
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "Sign up",

                modifier = Modifier.clickable {

                    val intent = Intent(
                        context,
                        RegistrationActivity::class.java
                    )

                    context.startActivity(intent)
                },

                color = Color(0xFF4A6CF7),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginBody()
}