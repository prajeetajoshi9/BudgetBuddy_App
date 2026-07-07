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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
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
import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.viewmodel.UserViewModel

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

    val userViewModel: UserViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFF))
            .verticalScroll(rememberScrollState())
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
            color = Color(0xFF7A7A7A),
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(35.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Full Name") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
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

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Home,
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

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = contact,
            onValueChange = { contact = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Contact") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
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

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Email") },
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

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = createPassword,
            onValueChange = { createPassword = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Create Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color(0xFF4A6CF7)
                )
            },
            trailingIcon = {
                Text(
                    text = if (visibility) "Hide" else "Show",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            visibility = !visibility
                        },
                    color = Color(0xFF4A6CF7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            },
            visualTransformation =
                if (visibility) VisualTransformation.None
                else PasswordVisualTransformation(),
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
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Confirm Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color(0xFF4A6CF7)
                )
            },
            trailingIcon = {
                Text(
                    text = if (confirmVisibility) "Hide" else "Show",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            confirmVisibility = !confirmVisibility
                        },
                    color = Color(0xFF4A6CF7),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            },
            visualTransformation =
                if (confirmVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
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

                if (
                    fullName.isEmpty() ||
                    address.isEmpty() ||
                    contact.isEmpty() ||
                    email.isEmpty() ||
                    createPassword.isEmpty() ||
                    confirmPassword.isEmpty()
                ) {
                    Toast.makeText(
                        context,
                        "Please fill all fields",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (createPassword != confirmPassword) {
                    Toast.makeText(
                        context,
                        "Password does not match",
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    val userModel = UserModel(
                        name = fullName,
                        email = email,
                        contact = contact,
                        address = address,
                        role = "user",
                        blocked = false
                    )

                    userViewModel.register(
                        email = email,
                        password = createPassword,
                        model = userModel
                    ) { success, message ->

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
                text = "Signup",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(
                text = "Already have an account?",
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "Login",
                modifier = Modifier.clickable {
                    activity?.finish()
                },
                color = Color(0xFF4A6CF7),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationPreview() {
    RegistrationBody()
}