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

class RegistrationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            RegistrationBody()

        }
    }
}

@Composable
fun RegistrationBody() {

    var fullName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var createPassword by remember { mutableStateOf("") }
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

        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "Sign Up",

            style = TextStyle(
                color = Color(0xFF4A6CF7),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Create your account",
            style = TextStyle(
                color = Color(0xFF7A7A7A),
                fontSize = 15.sp
            )
        )

        Spacer(modifier = Modifier.height(35.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Full Name")
            },

            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEAF0FF),
                unfocusedContainerColor = Color(0xFFEAF0FF),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = address,
            onValueChange = {
                address = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Address")
            },

            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEAF0FF),
                unfocusedContainerColor = Color(0xFFEAF0FF),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = contact,
            onValueChange = {
                contact = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Contact")
            },

            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEAF0FF),
                unfocusedContainerColor = Color(0xFFEAF0FF),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Email")
            },

            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEAF0FF),
                unfocusedContainerColor = Color(0xFFEAF0FF),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = createPassword,
            onValueChange = {
                createPassword = it
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
                                painterResource(
                                    R.drawable.baseline_visibility_off_24
                                ),

                        contentDescription = null,
                        tint = Color(0xFF4A6CF7)
                    )
                }
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text("Create Password")
            },

            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEAF0FF),
                unfocusedContainerColor = Color(0xFFEAF0FF),

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

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
                                painterResource(
                                    R.drawable.baseline_visibility_off_24
                                ),

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

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {

                if (createPassword == confirmPassword) {

                    val sharedPreferences =
                        context.getSharedPreferences(
                            "User",
                            Context.MODE_PRIVATE
                        )

                    val editor = sharedPreferences.edit()

                    editor.putString("fullName", fullName)
                    editor.putString("address", address)
                    editor.putString("contact", contact)
                    editor.putString("email", email)
                    editor.putString("password", createPassword)

                    editor.apply()

                    Toast.makeText(
                        context,
                        "Signup Success",
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
                text = "Signup",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {

            Text(
                "Already have an account?",
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                "Login",

                modifier = Modifier.clickable {

                    activity?.finish()

                },

                color = Color(0xFF4A6CF7),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationPreview() {

    RegistrationBody()

}