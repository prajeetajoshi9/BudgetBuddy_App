package com.example.budgetbuddy.view

import android.app.Activity
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.viewmodel.UserViewModel

class ForgetPasswordActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ForgetPasswordBody()
        }
    }
}

@Composable
fun ForgetPasswordBody() {

    var email by remember { mutableStateOf("") }

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
            text = "Forget Password",
            style = TextStyle(
                color = Color(0xFF4A6CF7),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Enter your registered email address",
            style = TextStyle(
                color = Color(0xFF7A7A7A),
                fontSize = 15.sp
            )
        )

        Spacer(modifier = Modifier.height(45.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Enter Email")
            },

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

        Spacer(modifier = Modifier.height(35.dp))

        Button(

            onClick = {

                if (email.isEmpty()) {

                    Toast.makeText(
                        context,
                        "Please enter your email",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    userViewModel.forgetPassword(email) { success, message ->

                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_LONG
                        ).show()

                        if (success) {
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
                text = "Send Reset Link",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Back to Login",

            modifier = Modifier.clickable {
                activity?.finish()
            },

            color = Color(0xFF4A6CF7),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForgetPreview() {
    ForgetPasswordBody()
}