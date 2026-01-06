package com.example.finova.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finova.ui.components.StyledTextField
import com.example.finova.ui.theme.FinovaMaroon
import com.example.finova.viewmodels.EditTransactionViewModel

@Composable
fun EditTransactionScreen(navController: NavController, viewModel: EditTransactionViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val transactionName by viewModel.transactionName.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val category by viewModel.category.collectAsState()
    val type by viewModel.type.collectAsState()

    LaunchedEffect(uiState.isTransactionSaved) {
        if (uiState.isTransactionSaved) {
            navController.popBackStack()
        }
    }

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Edit Transaction", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(32.dp))

                StyledTextField(
                    value = transactionName,
                    onValueChange = viewModel::onTransactionNameChange,
                    label = "Transaction Name",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                StyledTextField(
                    value = amount,
                    onValueChange = viewModel::onAmountChange,
                    label = "Amount",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                StyledTextField(
                    value = category,
                    onValueChange = viewModel::onCategoryChange,
                    label = "Category",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    RadioButton(selected = type == "income", onClick = { viewModel.onTypeChange("income") })
                    Text("Income", modifier = Modifier.align(Alignment.CenterVertically))
                    RadioButton(selected = type == "expense", onClick = { viewModel.onTypeChange("expense") })
                    Text("Expense", modifier = Modifier.align(Alignment.CenterVertically))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = viewModel::saveTransaction,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Transaction")
                }
            }
        }
    }
}
