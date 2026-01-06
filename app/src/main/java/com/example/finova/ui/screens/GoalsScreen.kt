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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finova.navigation.Screen
import com.example.finova.ui.components.GlassCard
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon
import com.example.finova.viewmodels.GoalsViewModel

@Composable
fun GoalsScreen(
    navController: NavController,
    goalsViewModel: GoalsViewModel = viewModel()
) {
    val uiState by goalsViewModel.uiState.collectAsStateWithLifecycle()

    androidx.compose.runtime.LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            android.widget.Toast.makeText(navController.context, it, android.widget.Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddGoal.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Goal")
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
                        text = "Your Goals",
                        style = MaterialTheme.typography.headlineMedium,
                        color = FinovaCream
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(uiState.goals) { goal ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = goal.name, style = MaterialTheme.typography.titleLarge, color = FinovaCream)
                                Row {
                                    IconButton(onClick = { navController.navigate("${Screen.EditGoal.route}/${goal.id}") }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit Goal", tint = FinovaCream)
                                    }
                                    IconButton(onClick = { goalsViewModel.deleteGoal(goal) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete Goal", tint = FinovaCream)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(text = String.format("Rs %.2f", goal.savedAmount), color = FinovaCream)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = String.format("Rs %.2f", goal.targetAmount), color = FinovaCream.copy(alpha = 0.7f))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = (goal.savedAmount / goal.targetAmount).toFloat(),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
