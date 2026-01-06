package com.example.finova.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.example.finova.viewmodels.AddGoalViewModel

@Composable
fun AddGoalScreen(navController: NavController, viewModel: AddGoalViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val goalName by viewModel.goalName.collectAsState()
    val targetAmount by viewModel.targetAmount.collectAsState()

    LaunchedEffect(uiState.isGoalSaved) {
        if (uiState.isGoalSaved) {
            navController.popBackStack()
        }
    }

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Add a New Goal", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))

            StyledTextField(
                value = goalName,
                onValueChange = viewModel::onGoalNameChange,
                label = "Goal Name",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            StyledTextField(
                value = targetAmount,
                onValueChange = viewModel::onTargetAmountChange,
                label = "Target Amount",
                modifier = Modifier.fillMaxWidth()
            )

            if (uiState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                androidx.compose.material3.CircularProgressIndicator(color = androidx.compose.ui.graphics.Color.White)
            }

            LaunchedEffect(uiState.errorMessage) {
                uiState.errorMessage?.let {
                    android.widget.Toast.makeText(navController.context, it, android.widget.Toast.LENGTH_LONG).show()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = viewModel::saveGoal,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Save Goal")
            }
        }
    }
}
