package com.example.budgetbuddy.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.example.budgetbuddy.utils.ThemeManager
import com.example.budgetbuddy.viewmodel.UserViewModel

class AdminManageUsersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themeManager = ThemeManager(this)

        setContent {
            BudgetBuddyTheme(
                darkTheme = themeManager.isDarkMode()
            ) {
                AdminManageUsersBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminManageUsersBody() {

    val context = LocalContext.current
    val activity = context as? Activity
    val userViewModel: UserViewModel = viewModel()
    val filter = activity?.intent?.getStringExtra("filter") ?: "all"

    var search by remember { mutableStateOf("") }
    var users by remember { mutableStateOf<List<UserModel>>(emptyList()) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteUserId by remember { mutableStateOf("") }

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val lightBlue = MaterialTheme.colorScheme.surface
    val darkText = MaterialTheme.colorScheme.onBackground
    val grayText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    val errorColor = MaterialTheme.colorScheme.error
    val successColor = Color(0xFF00A86B)
    val warningColor = Color(0xFFE65100)

    fun loadUsers() {
        userViewModel.getAllUsers { success, _, data ->
            if (success) {
                users = data.filter { it.role != "admin" }
            }
        }
    }

    LaunchedEffect(Unit) {
        loadUsers()
    }

    val filteredUsers = users.filter { user ->

        val matchesSearch =
            user.name.contains(search, ignoreCase = true) ||
                    user.email.contains(search, ignoreCase = true)

        val matchesFilter =
            if (filter == "blocked") user.blocked else true

        matchesSearch && matchesFilter
    }

    val activeUsers = users.count { !it.blocked }
    val blockedUsers = users.count { it.blocked }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Manage Users",
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            item {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search users") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = surfaceColor,
                        unfocusedContainerColor = surfaceColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(containerColor = primaryColor)
                ) {
                    Column(modifier = Modifier.padding(22.dp)) {
                        Text(
                            text = "Total Users",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${users.size}",
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "$activeUsers active users and $blockedUsers blocked users",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AdminUserStatusCard(
                        title = "Active",
                        value = "$activeUsers",
                        color = successColor.copy(alpha = 0.15f),
                        textColor = successColor,
                        modifier = Modifier.weight(1f)
                    )

                    AdminUserStatusCard(
                        title = "Blocked",
                        value = "$blockedUsers",
                        color = errorColor.copy(alpha = 0.15f),
                        textColor = errorColor,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Text(
                    text = "User List",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (filteredUsers.isEmpty()) {
                item {
                    Text(
                        text = "No users found.",
                        color = grayText,
                        fontSize = 14.sp
                    )
                }
            } else {
                items(filteredUsers) { user ->
                    AdminUserCard(
                        user = user,
                        primaryColor = primaryColor,
                        lightBlue = lightBlue,
                        darkText = darkText,
                        grayText = grayText,
                        surfaceColor = surfaceColor,
                        errorColor = errorColor,
                        successColor = successColor,
                        warningColor = warningColor,
                        onBlockClick = {
                            userViewModel.blockUser(
                                userId = user.userId,
                                blocked = !user.blocked
                            ) { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                if (success) loadUsers()
                            }
                        },
                        onDeleteClick = {
                            deleteUserId = user.userId
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete User") },
            text = { Text("Are you sure you want to delete this user?") },
            confirmButton = {
                Button(
                    onClick = {
                        userViewModel.deleteUser(deleteUserId) { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            if (success) loadUsers()
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = errorColor
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AdminUserStatusCard(
    title: String,
    value: String,
    color: Color,
    textColor: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = value,
                color = textColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AdminUserCard(
    user: UserModel,
    primaryColor: Color,
    lightBlue: Color,
    darkText: Color,
    grayText: Color,
    surfaceColor: Color,
    errorColor: Color,
    successColor: Color,
    warningColor: Color,
    onBlockClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val status = if (user.blocked) "Blocked" else "Active"
    val statusColor = if (user.blocked) errorColor else successColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(lightBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = primaryColor
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = user.name.ifEmpty { "No Name" },
                        color = darkText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = user.email,
                        color = grayText,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = status,
                    color = statusColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onBlockClick) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = warningColor
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = if (user.blocked) "Unblock" else "Block",
                        color = warningColor
                    )
                }

                TextButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = errorColor
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Delete",
                        color = errorColor
                    )
                }
            }
        }
    }
}