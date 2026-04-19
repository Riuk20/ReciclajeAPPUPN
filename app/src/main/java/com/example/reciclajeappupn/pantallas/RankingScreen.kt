package com.example.reciclajeappupn.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclajeappupn.RetrofitClient
import com.example.reciclajeappupn.pantallas.RankingItem
import com.example.reciclajeappupn.pantallas.HistorialItem

@Composable
fun RankingScreen() {
    var rankingList by remember { mutableStateOf(listOf<RankingItem>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            rankingList = RetrofitClient.instance.obtenerRanking()
        } catch (e: Exception) {
            // Error de conexión silencioso
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Líderes Eco 🏆",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFA000)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                itemsIndexed(rankingList) { index, user ->
                    ListItem(
                        headlineContent = { Text(user.nombre, fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("${user.puntosTotales} pts") },
                        leadingContent = {
                            Surface(
                                color = if (index < 3) Color(0xFFFFD700) else Color.LightGray,
                                shape = androidx.compose.foundation.shape.CircleShape,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${index + 1}",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    )
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }
    }
}