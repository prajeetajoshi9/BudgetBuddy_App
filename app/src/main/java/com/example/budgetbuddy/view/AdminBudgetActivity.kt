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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.model.BudgetModel
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.example.budgetbuddy.utils.ThemeManager
import com.example.budgetbuddy.viewmodel.BudgetViewModel

class AdminBudgetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themeManager = ThemeManager(this)

        setContent {
            BudgetBuddyTheme(
                darkTheme = themeManager.isDarkMode()
            ) {
                AdminBudgetBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminBudgetBody() {

    val context = LocalContext.current
    val activity = context as? Activity
    val budgetViewModel: BudgetViewModel = viewModel()

    var budgets by remember { mutableStateOf<List<BudgetModel>>(emptyList()) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteBudgetId by remember { mutableStateOf("") }

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val darkText = MaterialTheme.colorScheme.onBackground
    val grayText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    val errorColor = MaterialTheme.colorScheme.error

    fun loadBudgets() {
        budgetViewModel.getAllBudgets { success, _, data ->
            if (success) {
                budgets = data
            }
        }
    }

    LaunchedEffect(Unit) {
        loadBudgets()
    }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Budgets",
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(containerColor = primaryColor)
                ) {
                    Column(modifier = Modifier.padding(22.dp)) {
                        Text(
                            text = "Total Budgets",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${budgets.size}",
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (budgets.isEmpty()) {
                item {
                    Text(
                        text = "No budgets found.",
                        color = grayText
                    )
                }
            } else {
                items(budgets) { budget ->
                    AdminBudgetCard(
                        budget = budget,
                        darkText = darkText,
                        grayText = grayText,
                        primaryColor = primaryColor,
                        surfaceColor = surfaceColor,
                        errorColor = errorColor,
                        onDelete = {
                            deleteBudgetId = budget.budgetId
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
            title = { Text("Delete Budget") },
            text = { Text("Are you sure you want to delete this budget?") },
            confirmButton = {
                Button(
                    onClick = {
                        budgetViewModel.deleteBudget(deleteBudgetId) { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                            if (success) {
                                loadBudgets()
                            }
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
fun AdminBudgetCard(
    budget: BudgetModel,
    darkText: Color,
    grayText: Color,
    primaryColor: Color,
    surfaceColor: Color,
    errorColor: Color,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = budget.title,
                color = darkText,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Amount: Rs. ${budget.amount}",
                color = primaryColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Month: ${budget.month}",
                color = grayText,
                fontSize = 13.sp
            )

            Text(
                text = "User ID: ${budget.userId}",
                color = grayText,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDelete) {
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