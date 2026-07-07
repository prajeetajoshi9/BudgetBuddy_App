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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    val totalIncome = 40000
    val totalSpent = 15000
    val totalSaving = totalIncome - totalSpent

    val monthlyBudget = 20000
    val budgetUsed = totalSpent
    val budgetLeft = monthlyBudget - budgetUsed
    val budgetProgress = budgetUsed.toFloat() / monthlyBudget.toFloat()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
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
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = null)
                    },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.List, contentDescription = null)
                    },
                    label = { Text("Report") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.Add, contentDescription = null)
                    },
                    label = { Text("Add") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.AccountCircle, contentDescription = null)
                    },
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
                            text = "Hello User",
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
                            .background(Color(0xFFEAF0FF)),
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
                            text = "Total Balance",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. $totalSaving",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            BalanceInfo(
                                title = "Income",
                                amount = "Rs. $totalIncome",
                                modifier = Modifier.weight(1f)
                            )

                            BalanceInfo(
                                title = "Spent",
                                amount = "Rs. $totalSpent",
                                modifier = Modifier.weight(1f)
                            )

                            BalanceInfo(
                                title = "Saving",
                                amount = "Rs. $totalSaving",
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
                            text = "Rs. $budgetUsed used out of Rs. $monthlyBudget",
                            color = Color(0xFF7A7A7A),
                            fontSize = 13.sp
                        )

                        Text(
                            text = "Rs. $budgetLeft left",
                            color = Color(0xFF00A86B),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
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
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    ActionCard(
                        title = "Expense",
                        color = Color(0xFFFFEEEE),
                        textColor = Color(0xFFE53935),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    ActionCard(
                        title = "Reports",
                        color = Color(0xFFE8F7EF),
                        textColor = Color(0xFF00A86B),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Text(
                    text = "Expense Categories",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E1E1E)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    CategoryCard(
                        title = "Food",
                        amount = "Rs. 4,500",
                        color = Color(0xFFFFF4D9),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    CategoryCard(
                        title = "Travel",
                        amount = "Rs. 2,000",
                        color = Color(0xFFEAF0FF),
                        modifier = Modifier.weight(1f)
                    )
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
                        TransactionItem(
                            title = "Shopping",
                            category = "Lifestyle",
                            amount = "- Rs. 2500"
                        )

                        HorizontalDivider(color = Color(0xFFEAEAEA))

                        TransactionItem(
                            title = "Food",
                            category = "Restaurant",
                            amount = "- Rs. 500"
                        )

                        HorizontalDivider(color = Color(0xFFEAEAEA))

                        TransactionItem(
                            title = "Salary",
                            category = "Income",
                            amount = "+ Rs. 40,000"
                        )
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
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .height(70.dp)
            .clickable { },
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
fun CategoryCard(
    title: String,
    amount: String,
    color: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(70.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color(0xFF1E1E1E),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = amount,
                color = Color(0xFF7A7A7A),
                fontSize = 13.sp
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
            color =
                if (amount.startsWith("+"))
                    Color(0xFF00A86B)
                else
                    Color(0xFFE53935),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserDashboardPreview() {
    UserDashboardBody()
}