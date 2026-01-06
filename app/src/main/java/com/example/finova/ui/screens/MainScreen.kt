package com.example.finova.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finova.navigation.BottomNavigationBar
import com.example.finova.navigation.Screen

@Composable
fun MainScreen(navController: NavController) { // This is the main nav controller
    val bottomBarNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = bottomBarNavController) }
    ) { paddingValues ->
        NavHost(
            navController = bottomBarNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) { DashboardScreen(navController) }
            composable(Screen.Goals.route) { GoalsScreen(navController) }
            composable(Screen.Transactions.route) { TransactionsScreen(navController) }
            composable(Screen.Chat.route) { ChatScreen(navController) }
        }
    }
}