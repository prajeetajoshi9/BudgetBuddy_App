package com.example.budgetbuddy.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.model.ExpenseModel
import com.example.budgetbuddy.model.NotificationModel
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.example.budgetbuddy.utils.NotificationHelper
import com.example.budgetbuddy.utils.ThemeManager
import com.example.budgetbuddy.viewmodel.BudgetViewModel
import com.example.budgetbuddy.viewmodel.ExpenseViewModel
import com.example.budgetbuddy.viewmodel.NotificationViewModel
import com.example.budgetbuddy.viewmodel.UserViewModel

class ExpenseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }

        val themeManager = ThemeManager(this)

        setContent {
            BudgetBuddyTheme(
                darkTheme = themeManager.isDarkMode()
            ) {
                ExpenseBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseBody() {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var expenseList by remember { mutableStateOf<List<ExpenseModel>>(emptyList()) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteId by remember { mutableStateOf("") }


    val context = LocalContext.current
    val activity = context as? Activity

    val expenseViewModel: ExpenseViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val budgetViewModel: BudgetViewModel = viewModel()
    val notificationViewModel: NotificationViewModel = viewModel()

    val userId = userViewModel.getCurrentUserId() ?: ""

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val lightBlue = MaterialTheme.colorScheme.surface
    val darkText = MaterialTheme.colorScheme.onBackground
    val grayText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    val errorColor = MaterialTheme.colorScheme.error

    val categories = listOf("All", "Food", "Travel", "Shopping", "Bills", "Others")
    var selectedCategory by remember { mutableStateOf("All") }

    fun loadExpenses() {
        if (userId.isNotEmpty()) {
            expenseViewModel.getExpenseByUser(userId) { success, _, expenses ->
                if (success) {
                    expenseList = expenses
                }
            }
        }
    }

    LaunchedEffect(userId) {
        loadExpenses()
    }

    val filteredExpenses =
        if (selectedCategory == "All") expenseList
        else expenseList.filter { it.category == selectedCategory }

    val totalExpense = expenseList.sumOf { it.amount }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Expense",
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
                            text = "Total Expense",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. $totalExpense",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${expenseList.size} expenses added",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Add New Expense",
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
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Expense Title") },
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
                            value = amount,
                            onValueChange = { amount = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Amount") },
                            shape = RoundedCornerShape(15.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = lightBlue,
                                unfocusedContainerColor = lightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            OutlinedTextField(
                                value = category,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                placeholder = {
                                    Text("Select Category")
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = lightBlue,
                                    unfocusedContainerColor = lightBlue,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {
                                listOf("Food", "Travel", "Shopping", "Bills", "Others").forEach { item ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(item)
                                        },
                                        onClick = {
                                            category = item
                                            expanded = false

                                            if (item != "Others") {
                                                note = ""
                                            }
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        if (category == "Others") {

                            Spacer(modifier = Modifier.height(14.dp))

                            OutlinedTextField(
                                value = note,
                                onValueChange = { note = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text("Write other category note")
                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = lightBlue,
                                    unfocusedContainerColor = lightBlue,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )
                        }

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

                                if (title.isEmpty() || amount.isEmpty() || category.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please fill title, amount and category",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    val amountDouble = amount.toDoubleOrNull()

                                    if (amountDouble == null) {
                                        Toast.makeText(
                                            context,
                                            "Enter valid amount",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        val expenseModel = ExpenseModel(
                                            userId = userId,
                                            title = title,
                                            amount = amountDouble,
                                            category = category,
                                            note = note,
                                            date = "Today"
                                        )

                                        expenseViewModel.addExpense(expenseModel) { success, message ->

                                            Toast.makeText(
                                                context,
                                                message,
                                                Toast.LENGTH_LONG
                                            ).show()

                                            if (success) {

                                                notificationViewModel.addNotification(
                                                    NotificationModel(
                                                        title = "Expense Added",
                                                        message = "${expenseModel.title} expense of Rs. ${expenseModel.amount} was added.",
                                                        date = System.currentTimeMillis().toString(),
                                                        target = userId,
                                                        read = false
                                                    )
                                                ) { _, _ -> }

                                                title = ""
                                                amount = ""
                                                category = ""
                                                note = ""

                                                loadExpenses()

                                                budgetViewModel.getBudgetByUser(userId) { budgetSuccess, _, budgets ->

                                                    if (budgetSuccess) {

                                                        expenseViewModel.getExpenseByUser(userId) { expenseSuccess, _, expenses ->

                                                            if (expenseSuccess) {

                                                                val totalBudget = budgets.sumOf { it.amount }
                                                                val totalSpent = expenses.sumOf { it.amount }

                                                                if (totalBudget > 0) {

                                                                    val usedPercent =
                                                                        (totalSpent / totalBudget) * 100

                                                                    if (totalSpent > totalBudget) {

                                                                        NotificationHelper.showBudgetNotification(
                                                                            context = context,
                                                                            title = "Budget Crossed",
                                                                            message = "You spent Rs. $totalSpent out of Rs. $totalBudget."
                                                                        )

                                                                        notificationViewModel.addNotification(NotificationModel(
                                                                            title = "Budget Crossed",
                                                                            message = "You spent Rs. $totalSpent out of Rs. $totalBudget.",
                                                                            date = System.currentTimeMillis().toString(),
                                                                            target = userId,
                                                                            read = false
                                                                        )
                                                                        ) { _, _ -> }

                                                                    } else if (usedPercent >= 75) {

                                                                        NotificationHelper.showBudgetNotification(
                                                                            context = context,
                                                                            title = "Budget Alert",
                                                                            message = "You used ${usedPercent.toInt()}% of your budget."
                                                                        )

                                                                        notificationViewModel.addNotification(
                                                                            NotificationModel(
                                                                                title = "Budget Alert",
                                                                                message = "You used ${usedPercent.toInt()}% of your budget.",
                                                                                date = System.currentTimeMillis().toString(),
                                                                                target = userId,
                                                                                read = false
                                                                            )
                                                                        ) { _, _ -> }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                Toast.makeText(
                                                    context,
                                                    "Expense added. Check dashboard for budget status.",
                                                    Toast.LENGTH_LONG
                                                ).show()
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
                                text = "Add Expense",
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
                    text = "Categories",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categories) { item ->
                        CategoryChip(
                            title = item,
                            selected = selectedCategory == item,
                            primaryColor = primaryColor,
                            lightBlue = lightBlue
                        ) {
                            selectedCategory = item
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Expense List",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (filteredExpenses.isEmpty()) {
                item {
                    Text(
                        text = "No expenses found.",
                        color = grayText,
                        fontSize = 14.sp
                    )
                }
            } else {
                items(filteredExpenses) { expense ->
                    ExpenseCard(
                        category = expense.category,
                        title = expense.title,
                        amount = "Rs. ${expense.amount}",
                        date = expense.date,
                        note = expense.note,
                        primaryColor = primaryColor,
                        darkText = darkText,
                        grayText = grayText,
                        errorColor = errorColor,
                        onDelete = {
                            deleteId = expense.expenseId
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
                Text("Delete Expense")
            },
            text = {
                Text("Are you sure you want to delete this expense?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        expenseViewModel.deleteExpense(deleteId) { success, message ->
                            Toast.makeText(
                                context,
                                message,
                                Toast.LENGTH_LONG
                            ).show()

                            if (success) {
                                loadExpenses()
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
                OutlinedButton(
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
fun CategoryChip(
    title: String,
    selected: Boolean,
    primaryColor: Color,
    lightBlue: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) primaryColor else lightBlue
        )
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
            color = if (selected) Color.White else primaryColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ExpenseCard(
    category: String,
    title: String,
    amount: String,
    date: String,
    note: String,
    primaryColor: Color,
    darkText: Color,
    grayText: Color,
    errorColor: Color,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = category,
                        color = primaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = title,
                        color = darkText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = date,
                        color = grayText,
                        fontSize = 13.sp
                    )

                    if (note.isNotEmpty()) {
                        Text(
                            text = note,
                            color = grayText,
                            fontSize = 12.sp
                        )
                    }
                }

                Text(
                    text = amount,
                    color = errorColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

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