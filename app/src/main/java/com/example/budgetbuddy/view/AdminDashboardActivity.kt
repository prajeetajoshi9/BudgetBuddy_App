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
import androidx.compose.material.icons.filled.BarChart
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

class AdminDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AdminDashboardBody()
        }
    }
}

@Composable
fun AdminDashboardBody() {

    val context = LocalContext.current

    val primaryColor = Color(0xFF4A6CF7)
    val backgroundColor = Color(0xFFF8FAFF)
    val lightBlue = Color(0xFFEAF0FF)
    val darkText = Color(0xFF1E1E1E)
    val grayText = Color(0xFF7A7A7A)

    Scaffold(
        containerColor = backgroundColor,

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
                    onClick = {
                        context.startActivity(
                            Intent(context, AdminManageUsersActivity::class.java)
                        )
                    },
                    icon = {
                        Icon(Icons.Default.Group, contentDescription = null)
                    },
                    label = { Text("Users") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(
                            Intent(context, AdminReportActivity::class.java)
                        )
                    },
                    icon = {
                        Icon(Icons.Default.List, contentDescription = null)
                    },
                    label = { Text("Reports") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(Icons.Default.AccountCircle, contentDescription = null)
                    },
                    label = { Text("Admin") }
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
                            .background(lightBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = primaryColor
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {
                        Text(
                            text = "Total Platform Expense",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. 8,50,000",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Across all active users",
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
                    AdminStatCard(
                        title = "Users",
                        value = "120",
                        color = lightBlue,
                        textColor = primaryColor,
                        modifier = Modifier.weight(1f)
                    )

                    AdminStatCard(
                        title = "Budgets",
                        value = "86",
                        color = Color(0xFFE8F7EF),
                        textColor = Color(0xFF00A86B),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AdminStatCard(
                        title = "Expenses",
                        value = "430",
                        color = Color(0xFFFFF4D9),
                        textColor = Color(0xFFE65100),
                        modifier = Modifier.weight(1f)
                    )

                    AdminStatCard(
                        title = "Blocked",
                        value = "4",
                        color = Color(0xFFFFEEEE),
                        textColor = Color(0xFFE53935),
                        modifier = Modifier.weight(1f)
                    )
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
                        color = Color(0xFFE8F7EF),
                        textColor = Color(0xFF00A86B),
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

            item {
                AdminRecentUserCard(
                    name = "Pratik Joshi",
                    email = "pratik@example.com",
                    status = "Active",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue,
                    darkText = darkText,
                    grayText = grayText
                )
            }

            item {
                AdminRecentUserCard(
                    name = "Aayush Sharma",
                    email = "aayush@example.com",
                    status = "Active",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue,
                    darkText = darkText,
                    grayText = grayText
                )
            }

            item {
                AdminRecentUserCard(
                    name = "Blocked User",
                    email = "blocked@example.com",
                    status = "Blocked",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue,
                    darkText = darkText,
                    grayText = grayText
                )
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
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
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
        colors = CardDefaults.cardColors(
            containerColor = color
        )
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
    grayText: Color
) {
    val statusColor =
        if (status == "Blocked") Color(0xFFE53935) else Color(0xFF00A86B)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
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

            Column(
                modifier = Modifier.weight(1f)
            ) {
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