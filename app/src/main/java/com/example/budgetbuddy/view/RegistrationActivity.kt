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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.model.NotificationModel
import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.example.budgetbuddy.utils.ThemeManager
import com.example.budgetbuddy.viewmodel.NotificationViewModel
import com.example.budgetbuddy.viewmodel.UserViewModel
import androidx.compose.ui.platform.testTag

class RegistrationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themeManager = ThemeManager(this)

        setContent {
            BudgetBuddyTheme(
                darkTheme = themeManager.isDarkMode()
            ) {
                RegistrationBody()
            }
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

    val notificationViewModel: NotificationViewModel = viewModel()

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val fieldColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground
    val grayText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "Sign Up",
            style = TextStyle(
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Create your account",
            color = grayText,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(35.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            modifier = Modifier.fillMaxWidth()
                .testTag("fullName"),
            placeholder = { Text("Full Name") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null, tint = primaryColor)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier.fillMaxWidth()
                .testTag("address"),
            placeholder = { Text("Address") },
            leadingIcon = {
                Icon(Icons.Default.Home, contentDescription = null, tint = primaryColor)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = contact,
            onValueChange = { contact = it },
            Modifier
                .fillMaxWidth()
                .testTag("contact"),
            placeholder = { Text("Contact") },
            leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = null, tint = primaryColor)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth()
                .testTag("email"),
            placeholder = { Text("Email") },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = null, tint = primaryColor)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = createPassword,
            onValueChange = { createPassword = it },
            modifier = Modifier.fillMaxWidth()
                .testTag("createPassword"),
            placeholder = { Text("Create Password") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = primaryColor)
            },
            trailingIcon = {
                Text(
                    text = if (visibility) "Hide" else "Show",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable { visibility = !visibility },
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            },
            visualTransformation =
                if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth()
                .testTag("confirmPassword"),
            placeholder = { Text("Confirm Password") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = primaryColor)
            },
            trailingIcon = {
                Text(
                    text = if (confirmVisibility) "Hide" else "Show",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable { confirmVisibility = !confirmVisibility },
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            },
            visualTransformation =
                if (confirmVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
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
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
                } else if (createPassword != confirmPassword) {
                    Toast.makeText(context, "Password does not match", Toast.LENGTH_LONG).show()
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

                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                        if (success) {

                            notificationViewModel.addNotification(
                                NotificationModel(
                                    title = "New User Registered",
                                    message = "$fullName has joined BudgetBuddy.",
                                    date = System.currentTimeMillis().toString(),
                                    target = "admin",
                                    read = false
                                )
                            ) { success, message ->

                                if (!success) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }

                            context.startActivity(Intent(context, LoginActivity::class.java))
                            activity?.finish()
                        }
                    }

                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .testTag("signupButton"),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor
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
                color = textColor
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "Login",
                modifier = Modifier
                    .testTag("login")
                    .clickable {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    activity?.finish()
                },
                color = primaryColor,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}