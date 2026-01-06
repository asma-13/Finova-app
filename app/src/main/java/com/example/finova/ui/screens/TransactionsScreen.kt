package com.example.finova.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finova.navigation.Screen
import com.example.finova.ui.components.GlassCard
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaGreen
import com.example.finova.ui.theme.FinovaMaroon
import com.example.finova.ui.theme.FinovaRed
import com.example.finova.viewmodels.TransactionsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TransactionsScreen(
    navController: NavController,
    transactionsViewModel: TransactionsViewModel = viewModel()
) {
    val uiState by transactionsViewModel.uiState.collectAsStateWithLifecycle()
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    androidx.compose.runtime.LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            android.widget.Toast.makeText(navController.context, it, android.widget.Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddTransaction.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) {
        Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize().padding(it)) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "All Transactions",
                        style = MaterialTheme.typography.headlineMedium,
                        color = FinovaCream
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(uiState.transactions) { transaction ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = transaction.name, style = MaterialTheme.typography.titleLarge, color = FinovaCream)
                                    Text(text = transaction.category, style = MaterialTheme.typography.bodyMedium, color = FinovaCream.copy(alpha = 0.7f))
                                }
                                Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                                    Text(
                                        text = String.format("%sRs %.2f", if (transaction.type == "income") "+" else "-", transaction.amount),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = if (transaction.type == "income") FinovaGreen else FinovaRed
                                    )
                                    Text(
                                        text = dateFormatter.format(transaction.date.toDate()),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = FinovaCream.copy(alpha = 0.7f)
                                    )
                                }
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                IconButton(onClick = { navController.navigate("${Screen.EditTransaction.route}/${transaction.id}") }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit Transaction", tint = FinovaCream)
                                }
                                IconButton(onClick = { transactionsViewModel.deleteTransaction(transaction) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete Transaction", tint = FinovaCream)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
