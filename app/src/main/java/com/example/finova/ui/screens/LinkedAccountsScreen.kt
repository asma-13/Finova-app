package com.example.finova.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon

@Composable
fun LinkedAccountsScreen(navController: NavController) {
    val accounts = listOf(
        AccountItem("Chase Bank", "**** 1234", "Checking"),
        AccountItem("Citi Card", "**** 5678", "Credit Card"),
        AccountItem("PayPal", "user@example.com", "Digital Wallet")
    )

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(onClick = { /* TODO: Add Account */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Account")
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                Text(
                    text = "Linked Accounts",
                    style = MaterialTheme.typography.headlineMedium,
                    color = FinovaCream,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(accounts) { account ->
                        AccountCard(account)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountCard(account: AccountItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CreditCard,
                contentDescription = null,
                tint = FinovaCream,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = account.name, style = MaterialTheme.typography.titleMedium, color = FinovaCream)
                Text(text = account.number, style = MaterialTheme.typography.bodyMedium, color = FinovaCream.copy(alpha = 0.7f))
                Text(text = account.type, style = MaterialTheme.typography.labelSmall, color = FinovaCream.copy(alpha = 0.5f))
            }
        }
    }
}

data class AccountItem(val name: String, val number: String, val type: String)
