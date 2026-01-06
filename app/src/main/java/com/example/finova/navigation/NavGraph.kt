package com.example.finova.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finova.ui.screens.*

@Composable
fun FinovaNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(Screen.Dashboard.route) { MainScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.EditProfile.route) { EditProfileScreen(navController) }
        composable(Screen.ChangePassword.route) { ChangePasswordScreen(navController) }
        composable(Screen.Notifications.route) { NotificationsScreen(navController) }
        composable(Screen.Privacy.route) { PrivacyScreen(navController) }
        composable(Screen.LinkedAccounts.route) { LinkedAccountsScreen(navController) }
        composable(Screen.Help.route) { HelpScreen(navController) }
        composable(Screen.Feedback.route) { FeedbackScreen(navController) }
        composable(Screen.RateApp.route) { RateAppScreen(navController) }
        composable(Screen.AddGoal.route) { AddGoalScreen(navController) }
        composable("${Screen.EditGoal.route}/{goalId}") { 
            EditGoalScreen(navController)
        }
        composable(Screen.AddTransaction.route) { AddTransactionScreen(navController) }
        composable("${Screen.EditTransaction.route}/{transactionId}") { 
            EditTransactionScreen(navController)
        }
    }
}
