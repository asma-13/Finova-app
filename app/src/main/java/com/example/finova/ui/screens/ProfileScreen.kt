package com.example.finova.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finova.navigation.Screen
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon
import com.example.finova.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val userProfile = com.example.finova.ui.state.LocalUserProfile.current

    val profileItems = listOf(
        ProfileItem("Edit Profile", Icons.Default.AccountCircle, Screen.EditProfile.route),
        ProfileItem("Change Password", Icons.Default.Lock, Screen.ChangePassword.route),
        ProfileItem("Notifications", Icons.Default.Notifications, Screen.Notifications.route),
        ProfileItem("Privacy", Icons.Default.PrivacyTip, Screen.Privacy.route),
        ProfileItem("Linked Accounts", Icons.Default.Link, Screen.LinkedAccounts.route)
    )

    val supportItems = listOf(
        ProfileItem("Help & Support", Icons.Default.HelpOutline, Screen.Help.route),
        ProfileItem("Give Feedback", Icons.Default.ThumbUp, Screen.Feedback.route),
        ProfileItem("Rate the App", Icons.Default.StarRate, Screen.RateApp.route)
    )

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(80.dp),
                        tint = FinovaCream
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = userProfile?.name ?: "Finova User",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = FinovaCream
                    )
                    Text(
                        text = userProfile?.email ?: "",
                        fontSize = 16.sp,
                        color = FinovaCream.copy(alpha = 0.7f)
                    )
                }
            }

            // Profile Section
            item { SectionHeader("Profile") }
            items(profileItems) { item ->
                ProfileRow(item = item, onClick = { navController.navigate(item.route) })
            }

            // Support Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader("Support")
            }
            items(supportItems) { item ->
                ProfileRow(item = item, onClick = { navController.navigate(item.route) })
            }

            // Logout
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            profileViewModel.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Dashboard.route) { inclusive = true }
                            }
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout",
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Logout", color = MaterialTheme.colorScheme.error, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = FinovaCream.copy(alpha = 0.8f),
        modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
    )
}

@Composable
private fun ProfileRow(item: ProfileItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = item.icon, contentDescription = item.title, tint = FinovaCream)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = item.title, color = FinovaCream, fontSize = 16.sp)
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = FinovaCream.copy(alpha = 0.7f)
        )
    }
    Divider(color = FinovaCream.copy(alpha = 0.2f))
}

private data class ProfileItem(val title: String, val icon: ImageVector, val route: String)
