package com.example.budgetbuddy.view

import android.app.Activity
import android.os.Bundle
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.example.budgetbuddy.utils.ThemeManager
import com.example.budgetbuddy.viewmodel.BudgetViewModel
import com.example.budgetbuddy.viewmodel.ExpenseViewModel
import com.example.budgetbuddy.viewmodel.UserViewModel
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import android.content.Intent
import androidx.compose.ui.graphics.toArgb
import android.graphics.Color as AndroidColor
import androidx.core.content.FileProvider
import com.example.budgetbuddy.utils.PdfHelper

class AdminReportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themeManager = ThemeManager(this)

        setContent {
            BudgetBuddyTheme(
                darkTheme = themeManager.isDarkMode()
            ) {
                AdminReportBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReportBody() {

    val context = LocalContext.current
    val activity = context as? Activity

    val userViewModel: UserViewModel = viewModel()
    val budgetViewModel: BudgetViewModel = viewModel()
    val expenseViewModel: ExpenseViewModel = viewModel()

    var totalUsers by remember { mutableStateOf(0) }
    var activeUsers by remember { mutableStateOf(0) }
    var blockedUsers by remember { mutableStateOf(0) }
    var totalBudgets by remember { mutableStateOf(0) }
    var totalExpenseAmount by remember { mutableStateOf(0.0) }
    var categoryTotals by remember { mutableStateOf<Map<String, Double>>(emptyMap()) }

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val darkText = MaterialTheme.colorScheme.onBackground
    val grayText = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
    val errorColor = MaterialTheme.colorScheme.error
    val successColor = Color(0xFF00A86B)
    val warningColor = Color(0xFFFFA000)

    LaunchedEffect(Unit) {
        userViewModel.getAllUsers { success, _, users ->
            if (success) {
                totalUsers = users.size
                activeUsers = users.count { !it.blocked }
                blockedUsers = users.count { it.blocked }
            }
        }

        budgetViewModel.getAllBudgets { success, _, budgets ->
            if (success) {
                totalBudgets = budgets.size
            }
        }

        expenseViewModel.getAllExpenses { success, _, expenses ->
            if (success) {
                totalExpenseAmount = expenses.sumOf { it.amount }

                categoryTotals = expenses
                    .groupBy { it.category }
                    .mapValues { entry ->
                        entry.value.sumOf { it.amount }
                    }
            }
        }
    }

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
                            text = "Rs. $totalExpenseAmount",
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
                        value = "$totalUsers",
                        color = surfaceColor,
                        textColor = primaryColor,
                        modifier = Modifier.weight(1f)
                    )

                    AdminReportSmallCard(
                        title = "Budgets",
                        value = "$totalBudgets",
                        color = successColor.copy(alpha = 0.15f),
                        textColor = successColor,
                        modifier = Modifier.weight(1f)
                    )

                    AdminReportSmallCard(
                        title = "Blocked",
                        value = "$blockedUsers",
                        color = errorColor.copy(alpha = 0.15f),
                        textColor = errorColor,
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
                        containerColor = surfaceColor
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        AdminReportProgressRow(
                            title = "Active Users",
                            value = "$activeUsers",
                            progress = if (totalUsers > 0)
                                activeUsers.toFloat() / totalUsers
                            else 0f,
                            color = successColor,
                            trackColor = successColor.copy(alpha = 0.15f),
                            textColor = darkText
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AdminReportProgressRow(
                            title = "Blocked Users",
                            value = "$blockedUsers",
                            progress = if (totalUsers > 0)
                                blockedUsers.toFloat() / totalUsers
                            else 0f,
                            color = errorColor,
                            trackColor = errorColor.copy(alpha = 0.15f),
                            textColor = darkText
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

            if (categoryTotals.isEmpty()) {
                item {
                    Text(
                        text = "No category data available.",
                        color = grayText,
                        fontSize = 14.sp
                    )
                }
            } else {
                items(categoryTotals.toList()) { item ->

                    val percent =
                        if (totalExpenseAmount > 0) {
                            ((item.second / totalExpenseAmount) * 100).toInt()
                        } else {
                            0
                        }

                    AdminCategoryReportCard(
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
                Text(
                    text = "Expense Category Chart",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = surfaceColor
                    )
                ) {
                    if (categoryTotals.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No chart data available",
                                color = grayText
                            )
                        }
                    } else {
                        AdminExpenseBarChart(
                            categoryTotals = categoryTotals,
                            textColor = darkText
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        val file = PdfHelper.createReportPdf(
                            context = context,
                            totalBudget = totalBudgets.toDouble(),
                            totalExpense = totalExpenseAmount,
                            saving = 0.0,
                            categoryTotals = categoryTotals
                        )

                        if (file != null) {
                            val uri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.provider",
                                file
                            )

                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.setDataAndType(uri, "application/pdf")
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                            context.startActivity(intent)
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
                        imageVector = Icons.Default.List,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Download Admin Report",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
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
    trackColor: Color,
    textColor: Color
) {
    Column {
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
                text = value,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = progress.coerceIn(0f, 1f),
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
    progress: Float,
    primaryColor: Color,
    surfaceColor: Color,
    darkText: Color,
    grayText: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceColor
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
                        text = title.ifEmpty { "Uncategorized" },
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
fun AdminExpenseBarChart(
    categoryTotals: Map<String, Double>,
    textColor: Color
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(12.dp),
        factory = { context ->
            BarChart(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                description.isEnabled = false
                axisRight.isEnabled = false
                legend.isEnabled = true

                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                xAxis.setDrawGridLines(false)

                setDrawGridBackground(false)
            }
        },
        update = { chart ->

            val entries = ArrayList<BarEntry>()
            val labels = ArrayList<String>()

            categoryTotals.entries.forEachIndexed { index, item ->
                entries.add(
                    BarEntry(
                        index.toFloat(),
                        item.value.toFloat()
                    )
                )

                labels.add(item.key.ifEmpty { "Other" })
            }

            val dataSet = BarDataSet(entries, "Expenses")
            dataSet.color = AndroidColor.rgb(74, 108, 247)
            dataSet.valueTextSize = 12f
            dataSet.valueTextColor = textColor.toArgb()

            val data = BarData(dataSet)
            data.barWidth = 0.6f

            chart.data = data

            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            chart.xAxis.labelRotationAngle = -25f
            chart.xAxis.textColor = textColor.toArgb()

            chart.axisLeft.textColor = textColor.toArgb()
            chart.axisLeft.gridColor = AndroidColor.GRAY

            chart.legend.textColor = textColor.toArgb()

            chart.animateY(800)
            chart.invalidate()
        }
    )
}