package com.example.budgetbuddy.view

import android.app.Activity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.viewmodel.UserViewModel

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
    val userViewModel: UserViewModel = viewModel()

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
            color = Color(0xFF7A7A7A),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = { Text("Enter Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color(0xFF4A6CF7)
                )
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
            onValueChange = { password = it },
            visualTransformation =
                if (visibility) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                Text(
                    text = if (visibility) "Hide" else "Show",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable { visibility = !visibility },
                    color = Color(0xFF4A6CF7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color(0xFF4A6CF7)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = { Text("Enter Password") },
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
                    context.startActivity(
                        Intent(context, ForgetPasswordActivity::class.java)
                    )
                },
            color = Color(0xFF4A6CF7),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Please enter email and password",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    userViewModel.login(
                        email = email,
                        password = password
                    ) { success, message, role ->

                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_LONG
                        ).show()

                        if (success) {
                            if (role == "admin") {
                                context.startActivity(
                                    Intent(context, AdminDashboardActivity::class.java)
                                )
                            } else {
                                context.startActivity(
                                    Intent(context, UserDashboardActivity::class.java)
                                )
                            }

                            activity?.finish()
                        }
                    }
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
                    context.startActivity(
                        Intent(context, RegistrationActivity::class.java)
                    )
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