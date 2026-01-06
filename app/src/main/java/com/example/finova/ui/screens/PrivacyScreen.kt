package com.example.finova.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon

@Composable
fun PrivacyScreen(navController: NavController) {
    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Privacy Policy",
                style = MaterialTheme.typography.headlineMedium,
                color = FinovaCream,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Last updated: December 2025\n\n" +
                        "1. Data Collection\n" +
                        "We collect personal information that you provide to us, such as name, email address, and financial data.\n\n" +
                        "2. Use of Data\n" +
                        "We use your data to provide financial insights and improve our services.\n\n" +
                        "3. Data Security\n" +
                        "Your data is encrypted and stored securely on Google Firebase servers.\n\n" +
                        "4. Third-Party Services\n" +
                        "We do not sell your data to third parties.\n\n" +
                        "5. Contact Us\n" +
                        "If you have any questions about this Privacy Policy, please contact us at support@finova.com.",
                style = MaterialTheme.typography.bodyMedium,
                color = FinovaCream.copy(alpha = 0.8f)
            )
        }
    }
}
