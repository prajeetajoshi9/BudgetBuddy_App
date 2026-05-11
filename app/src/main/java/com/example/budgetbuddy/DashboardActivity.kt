package com.example.budgetbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DashboardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            DashboardBody()

        }
    }
}

@Composable
fun DashboardBody() {

    var selectedItem by remember {
        mutableIntStateOf(0)
    }

    val totalIncome = 40000
    val totalSpent = 15000
    val totalSaving = totalIncome - totalSpent

    val monthlyBudget = 20000
    val budgetUsed = totalSpent
    val budgetLeft = monthlyBudget - budgetUsed

    val budgetProgress =
        budgetUsed.toFloat() / monthlyBudget.toFloat()

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(

                onClick = { },

                containerColor = Color(0xFF4A6CF7)

            ) {

                Icon(
                    painter = painterResource(R.drawable.outline_add_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },

        bottomBar = {

            NavigationBar(
                containerColor = Color.White
            ) {

                NavigationBarItem(

                    selected = selectedItem == 0,

                    onClick = {
                        selectedItem = 0
                    },

                    icon = {

                        Icon(
                            painter = painterResource(R.drawable.outline_add_home_24),
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(

                    selected = selectedItem == 1,

                    onClick = {
                        selectedItem = 1
                    },

                    icon = {

                        Icon(
                            painter = painterResource(R.drawable.outline_bar_chart_24),
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Status")
                    }
                )

                NavigationBarItem(

                    selected = selectedItem == 2,

                    onClick = {
                        selectedItem = 2
                    },

                    icon = {

                        Icon(
                            painter = painterResource(R.drawable.outline_add_24),
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Add")
                    }
                )

                NavigationBarItem(

                    selected = selectedItem == 3,

                    onClick = {
                        selectedItem = 3
                    },

                    icon = {

                        Icon(
                            painter = painterResource(R.drawable.outline_account_circle_24),
                            contentDescription = null
                        )
                    },

                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFF))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Hello User",
                        color = Color(0xFF1E1E1E),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Welcome back to Budget Buddy",
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
                        painter = painterResource(R.drawable.outline_circle_notifications_24),
                        contentDescription = null,
                        tint = Color(0xFF4A6CF7)
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

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
                        "Total Balance",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Rs. $totalSaving",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                "Income",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "Rs. $totalIncome",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                "Spent",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "Rs. $totalSpent",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                "Saving",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                "Rs. $totalSaving",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        "Monthly Budget",
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
                        "Rs. $budgetUsed used out of Rs. $monthlyBudget",
                        color = Color(0xFF7A7A7A),
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        "Rs. $budgetLeft left",
                        color = Color(0xFF00A86B),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                "Quick Actions",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                ActionCard(
                    title = "Expense",
                    color = Color(0xFFFFEEEE),
                    textColor = Color(0xFFE53935),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(10.dp))

                ActionCard(
                    title = "Income",
                    color = Color(0xFFE8F7EF),
                    textColor = Color(0xFF00A86B),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(10.dp))

                ActionCard(
                    title = "Reports",
                    color = Color(0xFFEAF0FF),
                    textColor = Color(0xFF4A6CF7),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                "Expense Categories",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

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

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                "Recent Transactions",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {

                    TransactionItem(
                        title = "Shopping",
                        category = "Lifestyle",
                        amount = "- Rs. 2500"
                    )

                    Divider(color = Color(0xFFEAEAEA))

                    TransactionItem(
                        title = "Food",
                        category = "Restaurant",
                        amount = "- Rs. 500"
                    )

                    Divider(color = Color(0xFFEAEAEA))

                    TransactionItem(
                        title = "Salary",
                        category = "Income",
                        amount = "+ Rs. 40,000"
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
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
        modifier = modifier.height(70.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                title,
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
                title,
                color = Color(0xFF1E1E1E),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                amount,
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
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF1E1E1E)
            )

            Text(
                category,
                color = Color(0xFF7A7A7A),
                fontSize = 12.sp
            )
        }

        Text(
            amount,
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
fun DashboardPreview() {

    DashboardBody()

}