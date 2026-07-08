package com.example.budgetbuddy.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.model.UserModel
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.example.budgetbuddy.utils.ThemeManager
import com.example.budgetbuddy.viewmodel.BudgetViewModel
import com.example.budgetbuddy.viewmodel.ExpenseViewModel
import com.example.budgetbuddy.viewmodel.NotificationViewModel
import com.example.budgetbuddy.viewmodel.UserViewModel
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

class AdminDashboardActivity : ComponentActivity() {

    private var lastDarkMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themeManager = ThemeManager(this)
        lastDarkMode = themeManager.isDarkMode()

        setContent {
            BudgetBuddyTheme(
                darkTheme = themeManager.isDarkMode()
            ) {
                AdminDashboardBody()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val themeManager = ThemeManager(this)
        val currentDarkMode = themeManager.isDarkMode()

        if (currentDarkMode != lastDarkMode) {
            recreate()
        }
    }
}

@Composable
fun AdminDashboardBody() {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val userViewModel: UserViewModel = viewModel()
    val budgetViewModel: BudgetViewModel = viewModel()
    val expenseViewModel: ExpenseViewModel = viewModel()
    val notificationViewModel: NotificationViewModel = viewModel()

    var totalUsers by remember { mutableStateOf(0) }
    var blockedUsers by remember { mutableStateOf(0) }
    var totalBudgets by remember { mutableStateOf(0) }
    var totalExpenses by remember { mutableStateOf(0) }
    var totalExpenseAmount by remember { mutableStateOf(0.0) }
    var totalBudgetAmount by remember { mutableStateOf(0.0) }
    var recentUsers by remember { mutableStateOf<List<UserModel>>(emptyList()) }
    var unreadNotifications by remember { mutableStateOf(0) }

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val lightBlue = MaterialTheme.colorScheme.surface
    val darkText = MaterialTheme.colorScheme.onBackground
    val grayText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    val errorColor = MaterialTheme.colorScheme.error
    val successColor = Color(0xFF00A86B)

    fun loadAdminData() {
        userViewModel.getAllUsers { success, _, users ->
            if (success) {
                totalUsers = users.size
                blockedUsers = users.count { it.blocked }
                recentUsers = users.takeLast(3).reversed()
            }
        }

        budgetViewModel.getAllBudgets { success, _, budgets ->
            if (success) {
                totalBudgets = budgets.size
                totalBudgetAmount = budgets.sumOf { it.amount }
            }
        }

        expenseViewModel.getAllExpenses { success, _, expenses ->
            if (success) {
                totalExpenses = expenses.size
                totalExpenseAmount = expenses.sumOf { it.amount }
            }
        }

        notificationViewModel.getNotificationByUser("admin") { success, _, list ->
            if (success) {
                unreadNotifications = list.count { !it.read }
            }
        }
    }

    LaunchedEffect(Unit) {
        loadAdminData()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                loadAdminData()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            NavigationBar(containerColor = surfaceColor) {

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(
                            Intent(context, AdminManageUsersActivity::class.java)
                        )
                    },
                    icon = { Icon(Icons.Default.Group, contentDescription = null) },
                    label = { Text("Users") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(
                            Intent(context, AdminReportActivity::class.java)
                        )
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Reports") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(
                            Intent(context, UserProfileActivity::class.java)
                        )
                    },
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                    label = { Text("Profile") }
                )
            }
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Admin Dashboard",
                            color = darkText,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Manage BudgetBuddy users and reports",
                            color = grayText,
                            fontSize = 14.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(surfaceColor)
                            .clickable {
                                context.startActivity(
                                    Intent(context, NotificationActivity::class.java)
                                        .putExtra("target", "admin")
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        BadgedBox(
                            badge = {
                                if (unreadNotifications > 0) {
                                    Badge {
                                        Text(unreadNotifications.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = primaryColor
                            )
                        }
                    }
                }
            }

            item {
                val pagerState = rememberPagerState(pageCount = { 2 })

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->

                    if (page == 0) {
                        AdminTopSummaryCard(
                            title = "Total Platform Expense",
                            amount = "Rs. $totalExpenseAmount",
                            subtitle = "Across all active users",
                            color = primaryColor
                        )
                    } else {
                        AdminTopSummaryCard(
                            title = "Total Platform Budget",
                            amount = "Rs. $totalBudgetAmount",
                            subtitle = "Across all active users",
                            color = successColor
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    AdminStatCard(
                        title = "Users",
                        value = "$totalUsers",
                        color = lightBlue,
                        textColor = primaryColor,
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(
                            Intent(context, AdminManageUsersActivity::class.java)
                        )
                    }

                    AdminStatCard(
                        title = "Budgets",
                        value = "$totalBudgets",
                        color = successColor.copy(alpha = 0.15f),
                        textColor = successColor,
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(
                            Intent(context, AdminBudgetActivity::class.java)
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    AdminStatCard(
                        title = "Expenses",
                        value = "$totalExpenses",
                        color = Color(0xFFFFA000).copy(alpha = 0.15f),
                        textColor = Color(0xFFFFA000),
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(
                            Intent(context, AdminExpenseActivity::class.java)
                        )
                    }

                    AdminStatCard(
                        title = "Blocked",
                        value = "$blockedUsers",
                        color = errorColor.copy(alpha = 0.15f),
                        textColor = errorColor,
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(
                            Intent(context, AdminManageUsersActivity::class.java)
                                .putExtra("filter", "blocked")
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Quick Actions",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    AdminActionCard(
                        title = "Manage Users",
                        color = lightBlue,
                        textColor = primaryColor,
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(
                            Intent(context, AdminManageUsersActivity::class.java)
                        )
                    }

                    AdminActionCard(
                        title = "View Reports",
                        color = successColor.copy(alpha = 0.15f),
                        textColor = successColor,
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(
                            Intent(context, AdminReportActivity::class.java)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Recent Users",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (recentUsers.isEmpty()) {
                item {
                    Text(
                        text = "No users found",
                        color = grayText,
                        fontSize = 14.sp
                    )
                }
            } else {
                recentUsers.forEach { user ->
                    item {
                        AdminRecentUserCard(
                            name = user.name.ifEmpty { "No Name" },
                            email = user.email,
                            status = if (user.blocked) "Blocked" else "Active",
                            primaryColor = primaryColor,
                            lightBlue = lightBlue,
                            darkText = darkText,
                            grayText = grayText,
                            surfaceColor = surfaceColor,
                            errorColor = errorColor,
                            successColor = successColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(
    title: String,
    value: String,
    color: Color,
    textColor: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable { onClick() },
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

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = textColor,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AdminActionCard(
    title: String,
    color: Color,
    textColor: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(75.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AdminRecentUserCard(
    name: String,
    email: String,
    status: String,
    primaryColor: Color,
    lightBlue: Color,
    darkText: Color,
    grayText: Color,
    surfaceColor: Color,
    errorColor: Color,
    successColor: Color
) {
    val statusColor =
        if (status == "Blocked") errorColor else successColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(45.dp)
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
                    text = name,
                    color = darkText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Text(
                    text = email,
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
    }
}
@Composable
fun AdminTopSummaryCard(
    title: String,
    amount: String,
    subtitle: String,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Text(
                text = title,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = amount,
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp
            )
        }
    }
}