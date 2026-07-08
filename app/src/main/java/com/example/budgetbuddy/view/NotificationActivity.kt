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
import com.example.budgetbuddy.model.NotificationModel
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.example.budgetbuddy.utils.ThemeManager
import com.example.budgetbuddy.viewmodel.NotificationViewModel
import com.example.budgetbuddy.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themeManager = ThemeManager(this)

        setContent {
            BudgetBuddyTheme(
                darkTheme = themeManager.isDarkMode()
            ) {
                NotificationBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationBody() {

    val context = LocalContext.current
    val activity = context as? Activity

    val notificationViewModel: NotificationViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()

    val target = activity?.intent?.getStringExtra("target")
        ?: userViewModel.getCurrentUserId()
        ?: ""

    var notifications by remember {
        mutableStateOf<List<NotificationModel>>(emptyList())
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val darkText = MaterialTheme.colorScheme.onBackground
    val grayText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)

    val warningColor = Color(0xFFE65100)
    val successColor = Color(0xFF00A86B)
    val errorColor = MaterialTheme.colorScheme.error

    fun loadNotifications() {
        if (target.isNotEmpty()) {
            notificationViewModel.getNotificationByUser(target) { success, _, data ->
                if (success) {
                    notifications = data.sortedByDescending { it.date }
                }
            }
        }
    }

    LaunchedEffect(target) {
        loadNotifications()
    }


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
                            text = "${notifications.size} New",
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

            if (notifications.isEmpty()) {
                item {
                    Text(
                        text = "No notifications found.",
                        color = grayText,
                        fontSize = 14.sp
                    )
                }
            } else {
                items(notifications) { notification ->
                    NotificationCard(
                        notification = notification,
                        primaryColor = primaryColor,
                        surfaceColor = surfaceColor,
                        darkText = darkText,
                        grayText = grayText,
                        warningColor = warningColor,
                        successColor = successColor,
                        errorColor = errorColor,
                        onDelete = {
                            notificationViewModel.deleteNotification(
                                notification.notificationId
                            ) { success, message ->
                                Toast.makeText(
                                    context,
                                    message,
                                    Toast.LENGTH_LONG
                                ).show()

                                if (success) {
                                    loadNotifications()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: NotificationModel,
    primaryColor: Color,
    surfaceColor: Color,
    darkText: Color,
    grayText: Color,
    warningColor: Color,
    successColor: Color,
    errorColor: Color,
    onDelete: () -> Unit
) {
    val icon =
        when {
            notification.title.contains("Alert", ignoreCase = true) -> Icons.Default.Warning
            notification.title.contains("Crossed", ignoreCase = true) -> Icons.Default.Warning
            notification.title.contains("Added", ignoreCase = true) -> Icons.Default.CheckCircle
            else -> Icons.Default.Notifications
        }

    val iconColor =
        when {
            notification.title.contains("Alert", ignoreCase = true) -> warningColor
            notification.title.contains("Crossed", ignoreCase = true) -> errorColor
            notification.title.contains("Added", ignoreCase = true) -> successColor
            else -> primaryColor
        }

    val dateText = remember(notification.date) {
        val millis = notification.date.toLongOrNull() ?: 0L

        if (millis > 0) {
            SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
                .format(Date(millis))
        } else {
            notification.date
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceColor
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
                    .background(MaterialTheme.colorScheme.background),
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
                    text = notification.title,
                    color = darkText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.message,
                    color = grayText,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = dateText,
                    color = primaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = errorColor
                )
            }
        }
    }
}