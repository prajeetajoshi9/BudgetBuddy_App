package com.example.budgetbuddy.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
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
import androidx.compose.material.icons.filled.List
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.example.budgetbuddy.utils.PdfHelper
import com.example.budgetbuddy.utils.ThemeManager
import com.example.budgetbuddy.viewmodel.BudgetViewModel
import com.example.budgetbuddy.viewmodel.ExpenseViewModel
import com.example.budgetbuddy.viewmodel.UserViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.Calendar

class ReportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themeManager = ThemeManager(this)

        setContent {
            BudgetBuddyTheme(
                darkTheme = themeManager.isDarkMode()
            ) {
                ReportBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBody() {

    val context = LocalContext.current
    val activity = context as? Activity

    val userViewModel: UserViewModel = viewModel()
    val budgetViewModel: BudgetViewModel = viewModel()
    val expenseViewModel: ExpenseViewModel = viewModel()

    val userId = userViewModel.getCurrentUserId() ?: ""

    var totalBudget by remember { mutableStateOf(0.0) }
    var totalExpense by remember { mutableStateOf(0.0) }
    var categoryTotals by remember { mutableStateOf<Map<String, Double>>(emptyMap()) }
    var thisMonthExpense by remember { mutableStateOf(0.0) }
    var lastMonthExpense by remember { mutableStateOf(0.0) }

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val lightBlue = MaterialTheme.colorScheme.surface
    val darkText = MaterialTheme.colorScheme.onBackground
    val grayText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    val errorColor = MaterialTheme.colorScheme.error
    val successColor = Color(0xFF00A86B)

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            budgetViewModel.getBudgetByUser(userId) { success, _, budgets ->
                if (success) {
                    totalBudget = budgets.sumOf { it.amount }
                }
            }

            expenseViewModel.getExpenseByUser(userId) { success, _, expenses ->
                if (success) {
                    totalExpense = expenses.sumOf { it.amount }

                    categoryTotals = expenses.groupBy { it.category }
                        .mapValues { entry -> entry.value.sumOf { it.amount } }

                    val currentCalendar = Calendar.getInstance()
                    val currentMonth = currentCalendar.get(Calendar.MONTH)
                    val currentYear = currentCalendar.get(Calendar.YEAR)

                    val lastCalendar = Calendar.getInstance()
                    lastCalendar.add(Calendar.MONTH, -1)
                    val previousMonth = lastCalendar.get(Calendar.MONTH)
                    val previousYear = lastCalendar.get(Calendar.YEAR)

                    thisMonthExpense = expenses.filter { expense ->
                        val cal = Calendar.getInstance()
                        cal.timeInMillis = expense.createdAt
                        cal.get(Calendar.MONTH) == currentMonth &&
                                cal.get(Calendar.YEAR) == currentYear
                    }.sumOf { it.amount }

                    lastMonthExpense = expenses.filter { expense ->
                        val cal = Calendar.getInstance()
                        cal.timeInMillis = expense.createdAt
                        cal.get(Calendar.MONTH) == previousMonth &&
                                cal.get(Calendar.YEAR) == previousYear
                    }.sumOf { it.amount }
                }
            }
        }
    }

    val saving = totalBudget - totalExpense

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
                        Text("Monthly Report", color = Color.White.copy(alpha = 0.8f), fontSize = 15.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. $saving",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (saving >= 0) "Remaining from your budget" else "You crossed your budget",
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
                    ReportSmallCard("Budget", "Rs. $totalBudget", lightBlue, primaryColor, Modifier.weight(1f))
                    ReportSmallCard("Expense", "Rs. $totalExpense", errorColor.copy(alpha = 0.15f), errorColor, Modifier.weight(1f))
                    ReportSmallCard("Left", "Rs. $saving", successColor.copy(alpha = 0.15f), successColor, Modifier.weight(1f))
                }
            }

            item {
                Text("Expense Chart", color = darkText, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            }

            item {
                ExpensePieChartCard(
                    categoryTotals = categoryTotals,
                    surfaceColor = surfaceColor,
                    darkText = darkText,
                    grayText = grayText
                )
            }

            item {
                Text("Budget Summary", color = darkText, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        ComparisonRow("Total Budget", "Rs. $totalBudget", primaryColor, darkText)

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 10.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        ComparisonRow("Total Expense", "Rs. $totalExpense", errorColor, darkText)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (saving >= 0)
                                "You still have Rs. $saving remaining."
                            else
                                "You crossed your budget by Rs. ${-saving}.",
                            color = if (saving >= 0) successColor else errorColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            item {
                Text("Category Report", color = darkText, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            }

            if (categoryTotals.isEmpty()) {
                item {
                    Text("No expense data available.", color = grayText, fontSize = 14.sp)
                }
            } else {
                items(categoryTotals.toList()) { item ->
                    val percent =
                        if (totalExpense > 0) ((item.second / totalExpense) * 100).toInt()
                        else 0

                    CategoryReportCard(
                        title = item.first,
                        amount = "Rs. ${item.second}",
                        percent = "$percent%",
                        progress = percent / 100f,
                        primaryColor = primaryColor,
                        surfaceColor = surfaceColor,
                        darkText = darkText,
                        grayText = grayText
                    )
                }
            }

            item {
                Text("Month Comparison", color = darkText, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            }

            item {
                MonthComparisonCard(
                    thisMonth = thisMonthExpense,
                    lastMonth = lastMonthExpense,
                    primaryColor = primaryColor,
                    surfaceColor = surfaceColor,
                    darkText = darkText,
                    errorColor = errorColor,
                    successColor = successColor
                )
            }

            item {
                Button(
                    onClick = {
                        val file = PdfHelper.createReportPdf(
                            context = context,
                            totalBudget = totalBudget,
                            totalExpense = totalExpense,
                            saving = saving,
                            categoryTotals = categoryTotals
                        )

                        if (file != null) {
                            Toast.makeText(context, "Report downloaded successfully", Toast.LENGTH_LONG).show()

                            val uri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.provider",
                                file
                            )

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.setDataAndType(uri, "application/pdf")
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Failed to create report", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Icon(Icons.Default.List, contentDescription = null, tint = Color.White)

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
        colors = CardDefaults.cardColors(containerColor = color)
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
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ComparisonRow(
    title: String,
    amount: String,
    color: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = textColor,
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
    progress: Float,
    primaryColor: Color,
    surfaceColor: Color,
    darkText: Color,
    grayText: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title.ifEmpty { "Other" },
                        color = darkText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    Text(
                        text = amount,
                        color = grayText,
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
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(10.dp)),
                color = primaryColor,
                trackColor = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
fun ExpensePieChartCard(
    categoryTotals: Map<String, Double>,
    surfaceColor: Color,
    darkText: Color,
    grayText: Color
) {
    val isDark = MaterialTheme.colorScheme.background == Color(0xFF121212)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        if (categoryTotals.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No chart data available",
                    color = darkText,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Add expenses to see chart",
                    color = grayText,
                    fontSize = 13.sp
                )
            }
        } else {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(12.dp),
                factory = { context ->
                    PieChart(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        description.isEnabled = false
                        setUsePercentValues(true)
                        setEntryLabelColor(
                            if (isDark) android.graphics.Color.WHITE
                            else android.graphics.Color.BLACK
                        )
                        setEntryLabelTextSize(12f)
                        holeRadius = 45f
                        transparentCircleRadius = 50f
                        centerText = "Expenses"
                        setCenterTextSize(16f)
                        setCenterTextColor(
                            if (isDark) android.graphics.Color.WHITE
                            else android.graphics.Color.BLACK
                        )
                        legend.isEnabled = true
                        legend.textColor =
                            if (isDark) android.graphics.Color.WHITE
                            else android.graphics.Color.BLACK
                    }
                },
                update = { chart ->

                    val entries = categoryTotals.map {
                        PieEntry(
                            it.value.toFloat(),
                            it.key.ifEmpty { "Other" }
                        )
                    }

                    val dataSet = PieDataSet(entries, "Categories")

                    dataSet.colors = listOf(
                        android.graphics.Color.rgb(74, 108, 247),
                        android.graphics.Color.rgb(229, 57, 53),
                        android.graphics.Color.rgb(0, 168, 107),
                        android.graphics.Color.rgb(255, 193, 7),
                        android.graphics.Color.rgb(156, 39, 176)
                    )

                    val pieData = PieData(dataSet)
                    pieData.setValueTextSize(12f)
                    pieData.setValueTextColor(
                        if (isDark) android.graphics.Color.WHITE
                        else android.graphics.Color.BLACK
                    )

                    chart.data = pieData
                    chart.invalidate()
                }
            )
        }
    }
}

@Composable
fun MonthComparisonCard(
    thisMonth: Double,
    lastMonth: Double,
    primaryColor: Color,
    surfaceColor: Color,
    darkText: Color,
    errorColor: Color,
    successColor: Color
) {
    val difference = thisMonth - lastMonth

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {

            ComparisonRow(
                title = "This Month",
                amount = "Rs. $thisMonth",
                color = primaryColor,
                textColor = darkText
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            ComparisonRow(
                title = "Last Month",
                amount = "Rs. $lastMonth",
                color = errorColor,
                textColor = darkText
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text =
                    if (difference > 0)
                        "You spent Rs. $difference more than last month."
                    else if (difference < 0)
                        "Good job! You spent Rs. ${-difference} less than last month."
                    else
                        "Your spending is same as last month.",
                color =
                    if (difference > 0)
                        errorColor
                    else
                        successColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}