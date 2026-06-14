package com.musicapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    
    val searchResults = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else listOf(
            SearchResult("Midnight Dreams", "Alex Rivers", "Pop"),
            SearchResult("Electric Heart", "Neon Dreams", "Electronic"),
            SearchResult("Golden Hour", "JVKE", "Pop")
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF1C1C24)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    isSearching = it.isNotBlank()
                },
                placeholder = { Text("Search songs, artists, or lyrics...", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9D4EDD),
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Results
        AnimatedVisibility(
            visible = isSearching,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(searchResults) { result ->
                    SearchResultItem(result)
                }
            }
        }
        
        // Categories when not searching
        AnimatedVisibility(
            visible = !isSearching,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column {
                Text("Browse All", fontSize = 22.sp, color = Color.White)
                Spacer(modifier = Modifier.height(12.dp))
                
                CategoryGrid()
            }
        }
    }
}

@Composable
fun SearchResultItem(result: SearchResult) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1C1C24)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(result.title, color = Color.White, fontSize = 16.sp)
                Text("${result.artist} • ${result.genre}", color = Color.Gray, fontSize = 12.sp)
            }
            IconButton(onClick = {}) {
                Icon(
                    androidx.compose.material.icons.Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color(0xFF9D4EDD)
                )
            }
        }
    }
}

@Composable
fun CategoryGrid() {
    val categories = listOf("Pop Hits", "Electronic", "Hip Hop", "Chill Vibes", "Podcasts", "Rock Classics")
    
    androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF2C2C34)
            ) {
                Box(contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text(category, color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

data class SearchResult(val title: String, val artist: String, val genre: String)
