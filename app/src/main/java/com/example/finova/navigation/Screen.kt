
package com.example.finova.navigation

import androidx.annotation.DrawableRes
import com.example.finova.R

sealed class Screen(val route: String, val title: String, @DrawableRes val icon: Int = 0) {
    object Splash : Screen("splash", "Splash")
    object Login : Screen("login", "Login")
    object SignUp : Screen("signup", "Sign Up")
    object Dashboard : Screen("dashboard", "Dashboard")

    // Bottom Bar Screens
    object Home : Screen("home", "Home", R.drawable.ic_home)
    object Goals : Screen("goals", "Goals", R.drawable.ic_goals)
    object Transactions : Screen("transactions", "Spends", R.drawable.ic_spends)
    object Chat : Screen("chat", "Chat", R.drawable.ic_chat)

    object Profile : Screen("profile", "Profile")
    object EditProfile : Screen("editProfile", "Edit Profile")
    object ChangePassword : Screen("changePassword", "Change Password")
    object Notifications : Screen("notifications", "Notifications")
    object Privacy : Screen("privacy", "Privacy")
    object LinkedAccounts : Screen("linkedAccounts", "Linked Accounts")
    object Help : Screen("help", "Help")
    object Feedback : Screen("feedback", "Feedback")
    object RateApp : Screen("rateApp", "Rate App")
    object AddGoal : Screen("addGoal", "Add Goal")
    object EditGoal : Screen("editGoal", "Edit Goal")
    object AddTransaction : Screen("addTransaction", "Add Transaction")
    object EditTransaction : Screen("editTransaction", "Edit Transaction")
}
