package com.example.finova.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finova.ui.theme.FinovaCream
import com.example.finova.ui.theme.FinovaMaroon

@Composable
fun RateAppScreen(navController: NavController) {
    var rating by remember { mutableIntStateOf(0) }

    Surface(color = FinovaMaroon, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Rate Finova",
                style = MaterialTheme.typography.headlineMedium,
                color = FinovaCream,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "How do you like the app?",
                style = MaterialTheme.typography.bodyLarge,
                color = FinovaCream.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Row(horizontalArrangement = Arrangement.Center) {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "Star $i",
                        tint = if (i <= rating) Color(0xFFFFD700) else FinovaCream,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { rating = i }
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    Toast.makeText(navController.context, "Thanks for rating us $rating stars!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                enabled = rating > 0
            ) {
                Text("Submit Rating")
            }
        }
    }
}
