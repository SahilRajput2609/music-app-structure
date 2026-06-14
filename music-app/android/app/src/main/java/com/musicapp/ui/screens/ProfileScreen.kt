package com.musicapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    var userName by remember { mutableStateOf("Alex Rivers") }
    var userEmail by remember { mutableStateOf("alex@musicapp.com") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C24), Color(0xFF0A0A0F))
                )
            )
    ) {
        // Profile Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            // Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF9D4EDD), Color(0xFFFF66C4))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("👤", fontSize = 50.sp)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(userName, fontSize = 24.sp, color = Color.White)
            Text(userEmail, fontSize = 14.sp, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("1,294", "FOLLOWERS")
                StatItem("422", "FOLLOWING")
                StatItem("123", "AUDIO POINTS")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Menu Items
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProfileMenuItem("📊", "Sound Signature", "Advanced Audio Engine")
            ProfileMenuItem("☁️", "Cloudy Audio", "Daily Album Catalog")
            ProfileMenuItem("📤", "Share Profile", "Spread Audio")
            ProfileMenuItem("⚙️", "Preferences", "Connected Devices")
            ProfileMenuItem("🔒", "Privacy & Security", "GDPR, CCPA Compliant")
            ProfileMenuItem("❓", "Help & Support", "FAQ & Support")
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Logout Button
        Button(
            onClick = { /* Logout logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C34))
        ) {
            Text("LOG OUT", color = Color(0xFFFF6B6B))
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, color = Color.White)
        Text(label, fontSize = 10.sp, color = Color.Gray)
    }
}

@Composable
fun ProfileMenuItem(icon: String, title: String, subtitle: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1C1C24)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 24.sp)
            Column {
                Text(title, color = Color.White, fontSize = 16.sp)
                Text(subtitle, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}
