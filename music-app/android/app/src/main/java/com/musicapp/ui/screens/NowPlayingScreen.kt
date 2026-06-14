package com.musicapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayingScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0f) }
    
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C24), Color(0xFF0A0A0F))
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(32.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            // Album Art with rotation
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .rotate(if (isPlaying) rotation else 0f)
                    .shadow(30.dp, CircleShape)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFF9D4EDD), Color(0xFFFF66C4))
                        )
                    )
            ) {
                Text(
                    text = "🎵",
                    fontSize = 120.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Track Info
            Text(
                text = "Midnight Dreams",
                fontSize = 28.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = Color.White
            )
            
            Text(
                text = "Alex Rivers",
                fontSize = 18.sp,
                color = Color(0xFFB0B0B8),
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Progress Bar
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF2C2C34))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(currentPosition)
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF9D4EDD), Color(0xFFFF66C4))
                                )
                            )
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("0:00", color = Color.Gray, fontSize = 12.sp)
                    Text("3:45", color = Color.Gray, fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Player Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Shuffle,
                        contentDescription = "Shuffle",
                        tint = Color.White
                    )
                }
                
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        tint = Color.White
                    )
                }
                
                // Play/Pause Button
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .shadow(20.dp, CircleShape, spotColor = Color(0xFF9D4EDD))
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0xFF9D4EDD), Color(0xFF6B2FA5))
                            )
                        )
                        .clickable { isPlaying = !isPlaying }
                ) {
                    Icon(
                        imageVector = if (isPlaying) 
                            androidx.compose.material.icons.Icons.Default.Pause
                        else 
                            androidx.compose.material.icons.Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(40.dp)
                    )
                }
                
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = Color.White
                    )
                }
                
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Repeat,
                        contentDescription = "Repeat",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
