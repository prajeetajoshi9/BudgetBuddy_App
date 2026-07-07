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
import androidx.compose.material.icons.filled.Add
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
import com.example.budgetbuddy.viewmodel.BudgetViewModel
import com.example.budgetbuddy.viewmodel.UserViewModel

class BudgetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BudgetBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetBody() {

    var budgetTitle by remember { mutableStateOf("") }
    var budgetAmount by remember { mutableStateOf("") }
    var budgetMonth by remember { mutableStateOf("") }
    var budgetList by remember { mutableStateOf<List<BudgetModel>>(emptyList()) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedBudgetId by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as? Activity

    val budgetViewModel: BudgetViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()

    val userId = userViewModel.getCurrentUserId() ?: ""


    val primaryColor = Color(0xFF4A6CF7)
    val backgroundColor = Color(0xFFF8FAFF)
    val lightBlue = Color(0xFFEAF0FF)
    val darkText = Color(0xFF1E1E1E)
    val grayText = Color(0xFF7A7A7A)

    fun loadBudgets() {
        if (userId.isNotEmpty()) {
            budgetViewModel.getBudgetByUser(userId) { success, _, budgets ->
                if (success) {
                    budgetList = budgets
                }
            }
        }
    }

    LaunchedEffect(userId) {
        loadBudgets()
    }

    val totalBudget = budgetList.sumOf { it.amount }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Budget",
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(containerColor = primaryColor)
                ) {
                    Column(modifier = Modifier.padding(22.dp)) {
                        Text(
                            text = "Total Budget",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. $totalBudget",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${budgetList.size} budget records added",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Add New Budget",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {

                        OutlinedTextField(
                            value = budgetTitle,
                            onValueChange = { budgetTitle = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Budget Title") },
                            shape = RoundedCornerShape(15.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = lightBlue,
                                unfocusedContainerColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = budgetAmount,
                            onValueChange = { budgetAmount = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Budget Amount") },
                            shape = RoundedCornerShape(15.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = lightBlue,
                                unfocusedContainerColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = budgetMonth,
                            onValueChange = { budgetMonth = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Month Example: July 2026") },
                            shape = RoundedCornerShape(15.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = lightBlue,
                                unfocusedContainerColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = {
                                if (userId.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "User not logged in. Please login again.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@Button
                                }
                                if (budgetTitle.isEmpty() || budgetAmount.isEmpty() || budgetMonth.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please fill all fields",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    val amountDouble = budgetAmount.toDoubleOrNull()

                                    if (amountDouble == null) {
                                        Toast.makeText(
                                            context,
                                            "Enter valid amount",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        val budgetModel = BudgetModel(
                                            userId = userId,
                                            title = budgetTitle,
                                            amount = amountDouble,
                                            month = budgetMonth
                                        )

                                        budgetViewModel.addBudget(budgetModel) { success, message ->

                                            Toast.makeText(
                                                context,
                                                message,
                                                Toast.LENGTH_LONG
                                            ).show()

                                            if (success) {
                                                budgetTitle = ""
                                                budgetAmount = ""
                                                budgetMonth = ""
                                                loadBudgets()
                                            }
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Save Budget",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Your Budgets",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (budgetList.isEmpty()) {
                item {
                    Text(
                        text = "No budget added yet.",
                        color = grayText,
                        fontSize = 14.sp
                    )
                }
            } else {
                items(budgetList) { budget ->
                    BudgetHistoryCard(
                        title = budget.title,
                        amount = "Rs. ${budget.amount}",
                        used = budget.month,
                        onDelete = {
                            selectedBudgetId = budget.budgetId
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }
    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete Budget")
            },

            text = {
                Text("Are you sure you want to delete this budget?")
            },

            confirmButton = {

                Button(

                    onClick = {

                        budgetViewModel.deleteBudget(
                            selectedBudgetId
                        ) { success, message ->

                            Toast.makeText(
                                context,
                                message,
                                Toast.LENGTH_LONG
                            ).show()

                            if (success) {
                                loadBudgets()
                            }

                        }

                        showDeleteDialog = false
                    }

                ) {
                    Text("Delete")
                }

            },

            dismissButton = {

                TextButton(

                    onClick = {
                        showDeleteDialog = false
                    }

                ) {
                    Text("Cancel")
                }

            }
        )
    }
}

@Composable
fun BudgetHistoryCard(
    title: String,
    amount: String,
    used: String,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                    text = used,
                    color = Color(0xFF7A7A7A),
                    fontSize = 13.sp
                )
            }

            Row {
                Text(
                    text = amount,
                    color = Color(0xFF4A6CF7),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color(0xFFE53935)
                    )
                }
            }
        }
    }
}