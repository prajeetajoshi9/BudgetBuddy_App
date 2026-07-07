package com.example.budgetbuddy.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
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

class ReportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReportBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBody() {

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
                        text = "Report",
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
                            text = "Monthly Report",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "June 2026",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "You saved Rs. 25,000 this month",
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
                    ReportSmallCard(
                        title = "Income",
                        amount = "Rs. 40k",
                        color = Color(0xFFE8F7EF),
                        textColor = Color(0xFF00A86B),
                        modifier = Modifier.weight(1f)
                    )

                    ReportSmallCard(
                        title = "Expense",
                        amount = "Rs. 15k",
                        color = Color(0xFFFFEEEE),
                        textColor = Color(0xFFE53935),
                        modifier = Modifier.weight(1f)
                    )

                    ReportSmallCard(
                        title = "Saving",
                        amount = "Rs. 25k",
                        color = lightBlue,
                        textColor = primaryColor,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Text(
                    text = "Expense Chart",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
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
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(lightBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(45.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Chart will be shown here",
                            color = darkText,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Pie chart / Bar chart will be added later",
                            color = grayText,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Month Comparison",
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
                        ComparisonRow(
                            title = "This Month",
                            amount = "Rs. 15,000",
                            color = primaryColor
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 10.dp),
                            color = Color(0xFFEAEAEA)
                        )

                        ComparisonRow(
                            title = "Last Month",
                            amount = "Rs. 18,500",
                            color = Color(0xFFE53935)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "You spent Rs. 3,500 less than last month",
                            color = Color(0xFF00A86B),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Category Report",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                CategoryReportCard(
                    title = "Food",
                    amount = "Rs. 4,500",
                    percent = "30%",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue
                )
            }

            item {
                CategoryReportCard(
                    title = "Travel",
                    amount = "Rs. 2,000",
                    percent = "13%",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue
                )
            }

            item {
                CategoryReportCard(
                    title = "Shopping",
                    amount = "Rs. 6,000",
                    percent = "40%",
                    primaryColor = primaryColor,
                    lightBlue = lightBlue
                )
            }

            item {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Download Report",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun ReportSmallCard(
    title: String,
    amount: String,
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
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = amount,
                color = textColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ComparisonRow(
    title: String,
    amount: String,
    color: Color
) {
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
            text = amount,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CategoryReportCard(
    title: String,
    amount: String,
    percent: String,
    primaryColor: Color,
    lightBlue: Color
) {
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
                progress = 0.4f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),
                color = primaryColor,
                trackColor = lightBlue
            )
        }
    }
}