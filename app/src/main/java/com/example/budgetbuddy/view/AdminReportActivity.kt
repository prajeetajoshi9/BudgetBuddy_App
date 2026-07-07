package com.example.budgetbuddy.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AdminReportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AdminReportBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReportBody() {

    val primaryColor = Color(0xFF4A6CF7)
    val backgroundColor = Color(0xFFF8FAFF)
    val lightBlue = Color(0xFFEAF0FF)
    val darkText = Color(0xFF1E1E1E)
    val grayText = Color(0xFF7A7A7A)

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Admin Reports",
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
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
                            text = "Total App Expense",
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
                            text = "Combined expenses from all users",
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
                    AdminReportSmallCard(
                        title = "Users",
                        value = "120",
                        color = lightBlue,
                        textColor = primaryColor,
                        modifier = Modifier.weight(1f)
                    )

                    AdminReportSmallCard(
                        title = "Budgets",
                        value = "86",
                        color = Color(0xFFE8F7EF),
                        textColor = Color(0xFF00A86B),
                        modifier = Modifier.weight(1f)
                    )

                    AdminReportSmallCard(
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
                    text = "User Status",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
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
                        AdminReportProgressRow(
                            title = "Active Users",
                            value = "116",
                            progress = 0.96f,
                            color = Color(0xFF00A86B),
                            trackColor = Color(0xFFE8F7EF)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AdminReportProgressRow(
                            title = "Blocked Users",
                            value = "4",
                            progress = 0.04f,
                            color = Color(0xFFE53935),
                            trackColor = Color(0xFFFFEEEE)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Top Spending Categories",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                AdminCategoryReportCard(
                    title = "Shopping",
                    amount = "Rs. 2,40,000",
                    percent = "40%",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue
                )
            }

            item {
                AdminCategoryReportCard(
                    title = "Food",
                    amount = "Rs. 1,80,000",
                    percent = "30%",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue
                )
            }

            item {
                AdminCategoryReportCard(
                    title = "Travel",
                    amount = "Rs. 1,20,000",
                    percent = "20%",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue
                )
            }

            item {
                Text(
                    text = "Monthly Statistics",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null,
                            tint = primaryColor,
                            modifier = Modifier.size(50.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Admin Chart Placeholder",
                            color = darkText,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Monthly user and expense chart will be added later",
                            color = grayText,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminReportSmallCard(
    title: String,
    value: String,
    color: Color,
    textColor: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = value,
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AdminReportProgressRow(
    title: String,
    value: String,
    progress: Float,
    color: Color,
    trackColor: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = Color(0xFF1E1E1E),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = value,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(10.dp)),
            color = color,
            trackColor = trackColor
        )
    }
}

@Composable
fun AdminCategoryReportCard(
    title: String,
    amount: String,
    percent: String,
    primaryColor: Color,
    lightBlue: Color
) {
    val progressValue =
        when (percent) {
            "40%" -> 0.4f
            "30%" -> 0.3f
            "20%" -> 0.2f
            else -> 0.1f
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title,
                        color = Color(0xFF1E1E1E),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    Text(
                        text = amount,
                        color = Color(0xFF7A7A7A),
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = percent,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = progressValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(10.dp)),
                color = primaryColor,
                trackColor = lightBlue
            )
        }
    }
}