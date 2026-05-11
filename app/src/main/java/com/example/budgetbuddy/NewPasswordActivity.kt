package com.example.budgetbuddy

import android.app.Activity
import android.content.Context
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

class NewPasswordActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            NewPasswordBody()

        }
    }
}

@Composable
fun NewPasswordBody() {

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var visibility by remember { mutableStateOf(false) }
    var confirmVisibility by remember { mutableStateOf(false) }

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
            text = "Create New Password",

            style = TextStyle(
                color = Color(0xFF4A6CF7),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Your new password must be different",

            style = TextStyle(
                color = Color(0xFF7A7A7A),
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(45.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
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

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("New Password")
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
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },

            visualTransformation =
                if (confirmVisibility)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

            trailingIcon = {

                IconButton(
                    onClick = {
                        confirmVisibility = !confirmVisibility
                    }
                ) {

                    Icon(
                        painter =
                            if (confirmVisibility)
                                painterResource(R.drawable.baseline_visibility_24)
                            else
                                painterResource(R.drawable.baseline_visibility_off_24),

                        contentDescription = null,
                        tint = Color(0xFF4A6CF7)
                    )
                }
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Confirm Password")
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

                if (newPassword == confirmPassword) {

                    val sharedPreferences =
                        context.getSharedPreferences(
                            "User",
                            Context.MODE_PRIVATE
                        )

                    val editor = sharedPreferences.edit()

                    editor.putString(
                        "password",
                        newPassword
                    )

                    editor.apply()

                    Toast.makeText(
                        context,
                        "Password Changed Successfully",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    Toast.makeText(
                        context,
                        "Password does not match",
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
                text = "Change Password",
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
fun NewPasswordPreview() {

    NewPasswordBody()

}