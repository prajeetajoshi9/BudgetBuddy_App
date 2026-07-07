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
import androidx.compose.material.icons.filled.Add
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
import com.example.budgetbuddy.model.ExpenseModel
import com.example.budgetbuddy.viewmodel.BudgetViewModel
import com.example.budgetbuddy.viewmodel.ExpenseViewModel
import com.example.budgetbuddy.viewmodel.UserViewModel

class UserDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UserDashboardBody()
        }
    }
}

@Composable
fun UserDashboardBody() {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val userViewModel: UserViewModel = viewModel()
    val budgetViewModel: BudgetViewModel = viewModel()
    val expenseViewModel: ExpenseViewModel = viewModel()

    val userId = userViewModel.getCurrentUserId() ?: ""

    var userName by remember { mutableStateOf("User") }
    var totalBudget by remember { mutableStateOf(0.0) }
    var totalSpent by remember { mutableStateOf(0.0) }
    var recentExpenses by remember { mutableStateOf<List<ExpenseModel>>(emptyList()) }

    fun loadDashboardData() {
        if (userId.isNotEmpty()) {

            userViewModel.getUserById(userId) { success, _, user ->
                if (success && user != null) {
                    userName = user.name
                }
            }

            budgetViewModel.getBudgetByUser(userId) { success, _, budgets ->
                if (success) {
                    totalBudget = budgets.sumOf { it.amount }
                }
            }

            expenseViewModel.getExpenseByUser(userId) { success, _, expenses ->
                if (success) {
                    totalSpent = expenses.sumOf { it.amount }
                    recentExpenses = expenses.takeLast(3).reversed()
                }
            }
        }
    }

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            loadDashboardData()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                loadDashboardData()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val budgetLeft = totalBudget - totalSpent

    val budgetProgress =
        if (totalBudget > 0) {
            (totalSpent / totalBudget).toFloat().coerceIn(0f, 1f)
        } else {
            0f
        }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.startActivity(Intent(context, ExpenseActivity::class.java))
                },
                containerColor = Color(0xFF4A6CF7)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },

        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, ReportActivity::class.java))
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Report") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, ExpenseActivity::class.java))
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    label = { Text("Add") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, UserProfileActivity::class.java))
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
                .background(Color(0xFFF8FAFF))
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
                            text = "Hello $userName",
                            color = Color(0xFF1E1E1E),
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Welcome back to BudgetBuddy",
                            color = Color(0xFF7A7A7A),
                            fontSize = 14.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEAF0FF))
                            .clickable {
                                context.startActivity(
                                    Intent(context, NotificationActivity::class.java)
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = Color(0xFF4A6CF7)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4A6CF7)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Remaining Balance",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. $budgetLeft",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            BalanceInfo(
                                title = "Budget",
                                amount = "Rs. $totalBudget",
                                modifier = Modifier.weight(1f)
                            )

                            BalanceInfo(
                                title = "Spent",
                                amount = "Rs. $totalSpent",
                                modifier = Modifier.weight(1f)
                            )

                            BalanceInfo(
                                title = "Left",
                                amount = "Rs. $budgetLeft",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Monthly Budget",
                            color = Color(0xFF1E1E1E),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        LinearProgressIndicator(
                            progress = budgetProgress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            color = Color(0xFF4A6CF7),
                            trackColor = Color(0xFFEAF0FF)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. $totalSpent used out of Rs. $totalBudget",
                            color = Color(0xFF7A7A7A),
                            fontSize = 13.sp
                        )

                        Text(
                            text = "Rs. $budgetLeft left",
                            color = if (budgetLeft < 0) Color(0xFFE53935) else Color(0xFF00A86B),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            if (budgetLeft < 0) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEEEE)
                        )
                    ) {
                        Text(
                            text = "Budget crossed! You spent more than your budget.",
                            modifier = Modifier.padding(16.dp),
                            color = Color(0xFFE53935),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Quick Actions",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E1E1E)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    ActionCard(
                        title = "Budget",
                        color = Color(0xFFEAF0FF),
                        textColor = Color(0xFF4A6CF7),
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(Intent(context, BudgetActivity::class.java))
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    ActionCard(
                        title = "Expense",
                        color = Color(0xFFFFEEEE),
                        textColor = Color(0xFFE53935),
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(Intent(context, ExpenseActivity::class.java))
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    ActionCard(
                        title = "Reports",
                        color = Color(0xFFE8F7EF),
                        textColor = Color(0xFF00A86B),
                        modifier = Modifier.weight(1f)
                    ) {
                        context.startActivity(Intent(context, ReportActivity::class.java))
                    }
                }
            }

            item {
                Text(
                    text = "Recent Transactions",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E1E1E)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                        if (recentExpenses.isEmpty()) {
                            Text(
                                text = "No recent transactions",
                                modifier = Modifier.padding(vertical = 14.dp),
                                color = Color(0xFF7A7A7A)
                            )
                        } else {
                            recentExpenses.forEachIndexed { index, expense ->
                                TransactionItem(
                                    title = expense.title,
                                    category = expense.category,
                                    amount = "- Rs. ${expense.amount}"
                                )

                                if (index != recentExpenses.lastIndex) {
                                    HorizontalDivider(color = Color(0xFFEAEAEA))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BalanceInfo(
    title: String,
    amount: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = amount,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}

@Composable
fun ActionCard(
    title: String,
    color: Color,
    textColor: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(70.dp)
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
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun TransactionItem(
    title: String,
    category: String,
    amount: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Color(0xFF1E1E1E)
            )

            Text(
                text = category,
                color = Color(0xFF7A7A7A),
                fontSize = 12.sp
            )
        }

        Text(
            text = amount,
            color = Color(0xFFE53935),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}