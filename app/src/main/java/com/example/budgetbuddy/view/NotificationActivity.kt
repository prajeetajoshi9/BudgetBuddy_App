package com.example.budgetbuddy.view

import android.os.Bundle
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NotificationBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationBody() {

    val primaryColor = Color(0xFF4A6CF7)
    val backgroundColor = Color(0xFFF8FAFF)
    val lightBlue = Color(0xFFEAF0FF)
    val darkText = Color(0xFF1E1E1E)
    val grayText = Color(0xFF7A7A7A)

    val notifications = listOf(
        Triple("Budget Alert", "You have used 75% of your monthly budget.", "Today"),
        Triple("Expense Added", "Food expense of Rs. 500 was added.", "Today"),
        Triple("Report Ready", "Your monthly report is ready to download.", "Yesterday"),
        Triple("Reminder", "Do not forget to add today's expenses.", "Jun 28")
    )

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notifications",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                            text = "Alerts & Reminders",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "4 New",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Stay updated with your budget activity",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Recent Notifications",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(notifications) { item ->
                NotificationCard(
                    title = item.first,
                    message = item.second,
                    date = item.third,
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
fun NotificationCard(
    title: String,
    message: String,
    date: String,
    primaryColor: Color,
    lightBlue: Color,
    darkText: Color,
    grayText: Color
) {
    val warningColor = Color(0xFFE65100)
    val successColor = Color(0xFF00A86B)

    val icon =
        when {
            title.contains("Alert", ignoreCase = true) -> Icons.Default.Warning
            title.contains("Added", ignoreCase = true) -> Icons.Default.CheckCircle
            else -> Icons.Default.Notifications
        }

    val iconColor =
        when {
            title.contains("Alert", ignoreCase = true) -> warningColor
            title.contains("Added", ignoreCase = true) -> successColor
            else -> primaryColor
        }

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
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(lightBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = darkText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message,
                    color = grayText,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = date,
                    color = primaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}