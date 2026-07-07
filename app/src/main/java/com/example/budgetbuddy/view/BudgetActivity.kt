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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                        text = "Budget",
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
                            text = "Monthly Budget",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. 20,000",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. 5,000 remaining",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                    }
                }
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
                        Text(
                            text = "Budget Progress",
                            color = darkText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        LinearProgressIndicator(
                            progress = 0.75f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = primaryColor,
                            trackColor = lightBlue
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Rs. 15,000 used out of Rs. 20,000",
                            color = grayText,
                            fontSize = 13.sp
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
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
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
                            placeholder = { Text("Month") },
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
                                imageVector = Icons.Default.Save,
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
                    text = "Category Budgets",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BudgetCategoryCard(
                        title = "Food",
                        amount = "Rs. 5,000",
                        color = Color(0xFFFFF4D9),
                        modifier = Modifier.weight(1f)
                    )

                    BudgetCategoryCard(
                        title = "Travel",
                        amount = "Rs. 3,000",
                        color = lightBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BudgetCategoryCard(
                        title = "Shopping",
                        amount = "Rs. 4,000",
                        color = Color(0xFFFFEEEE),
                        modifier = Modifier.weight(1f)
                    )

                    BudgetCategoryCard(
                        title = "Bills",
                        amount = "Rs. 8,000",
                        color = Color(0xFFE8F7EF),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Text(
                    text = "Budget History",
                    color = darkText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                BudgetHistoryCard(
                    title = "June Budget",
                    amount = "Rs. 20,000",
                    used = "Rs. 15,000 used"
                )
            }

            item {
                BudgetHistoryCard(
                    title = "May Budget",
                    amount = "Rs. 18,000",
                    used = "Rs. 17,000 used"
                )
            }
        }
    }
}

@Composable
fun BudgetCategoryCard(
    title: String,
    amount: String,
    color: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(80.dp),
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
                color = Color(0xFF1E1E1E),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = amount,
                color = Color(0xFF7A7A7A),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun BudgetHistoryCard(
    title: String,
    amount: String,
    used: String
) {
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

            Text(
                text = amount,
                color = Color(0xFF4A6CF7),
                fontWeight = FontWeight.Bold
            )
        }
    }
}