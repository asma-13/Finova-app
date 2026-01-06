package com.example.finova.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.finova.R
import com.example.finova.navigation.Screen
import com.example.finova.ui.components.GlassCard
import com.example.finova.ui.components.LineChart
import com.example.finova.ui.components.ProgressRing
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon
import com.example.finova.ui.theme.FinovaTheme
import com.example.finova.viewmodels.DashboardViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val uiState by dashboardViewModel.uiState.collectAsStateWithLifecycle()
    val userProfile = com.example.finova.ui.state.LocalUserProfile.current
    val goals = uiState.goals
    val transactions = uiState.transactions

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Welcome Back,", color = FinovaCream.copy(alpha = 0.7f))
                    Text(
                        text = userProfile?.name ?: "User",
                        color = FinovaCream,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_logo), // Placeholder for avatar
                    contentDescription = "Profile Avatar",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate(Screen.Profile.route) },
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Balance Card
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = "Total Balance", color = FinovaCream.copy(alpha = 0.7f))
                    val totalBalance = transactions.sumOf { if (it.type == "income") it.amount else -it.amount }
                    Text(
                        text = "Rs ${String.format("%.2f", totalBalance)}",
                        color = FinovaCream,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val income = transactions.filter { it.type == "income" }.sumOf { it.amount }
                        val expense = transactions.filter { it.type == "expense" }.sumOf { it.amount }
                        
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(painterResource(R.drawable.ic_spends), contentDescription = null, tint = androidx.compose.ui.graphics.Color.Green, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Income", color = FinovaCream.copy(alpha = 0.7f), fontSize = 12.sp)
                            }
                            Text("Rs ${String.format("%.0f", income)}", color = FinovaCream, fontWeight = FontWeight.SemiBold)
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(painterResource(R.drawable.ic_spends), contentDescription = null, tint = androidx.compose.ui.graphics.Color.Red, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Expense", color = FinovaCream.copy(alpha = 0.7f), fontSize = 12.sp)
                            }
                            Text("Rs ${String.format("%.0f", expense)}", color = FinovaCream, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Goals Section
            Text("Your Goals", style = androidx.compose.material3.MaterialTheme.typography.titleLarge, color = FinovaCream)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                goals.take(2).forEach { goal ->
                    GlassCard(modifier = Modifier.weight(1f)) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            ProgressRing(
                                progress = (goal.savedAmount / goal.targetAmount).toFloat(),
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = goal.name, color = FinovaCream, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Spending Chart
            Text("Recent Spending", style = androidx.compose.material3.MaterialTheme.typography.titleLarge, color = FinovaCream)
            Spacer(modifier = Modifier.height(16.dp))
            val spendingData = transactions.filter { it.type == "expense" }.map { it.amount }
            if (spendingData.isNotEmpty()) {
                LineChart(
                    data = spendingData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Recent Activity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent Activity", style = androidx.compose.material3.MaterialTheme.typography.titleLarge, color = FinovaCream)
                Text("See All", color = FinovaCream.copy(alpha = 0.5f), fontSize = 14.sp, modifier = Modifier.clickable { navController.navigate(Screen.Transactions.route) })
            }
            Spacer(modifier = Modifier.height(16.dp))
            
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                transactions.take(5).forEach { transaction ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(transaction.name, color = FinovaCream, fontWeight = FontWeight.Bold)
                                Text(transaction.category, color = FinovaCream.copy(alpha = 0.6f), fontSize = 12.sp)
                            }
                            Text(
                                text = "${if (transaction.type == "income") "+" else "-"} Rs ${String.format("%.0f", transaction.amount)}",
                                color = if (transaction.type == "income") androidx.compose.ui.graphics.Color.Green else androidx.compose.ui.graphics.Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(80.dp)) // Bottom padding
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    FinovaTheme {
        DashboardScreen(navController = rememberNavController())
    }
}
