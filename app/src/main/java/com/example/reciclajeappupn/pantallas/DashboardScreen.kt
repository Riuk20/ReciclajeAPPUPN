package com.example.reciclajeappupn.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
// IMPORTANTE: Asegúrate de que esta línea apunte a TU paquete
import com.example.reciclajeappupn.R
import com.example.reciclajeappupn.RetrofitClient

data class CategoriaData(val nombre: String, val iconRes: Int)

@Composable
fun DashboardScreen(userId: Int, navController: NavController) {
    var puntosTotales by remember { mutableIntStateOf(0) }

    // Detectar navegación para refrescar puntos al volver
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val categorias = listOf(
        CategoriaData("Plástico", R.drawable.ic_plastic),
        CategoriaData("Papel", R.drawable.ic_paper),
        CategoriaData("Vidrio", R.drawable.ic_glass),
        CategoriaData("Metal", R.drawable.ic_metal)
    )

    // Se dispara cada vez que entramos a la pantalla o volvemos de otra
    LaunchedEffect(navBackStackEntry) {
        try {
            val response = RetrofitClient.instance.obtenerPuntos(userId)
            if (response.status == "success") {
                puntosTotales = response.puntosTotales
            }
        } catch (e: Exception) {
            // Error de red
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Panel de Reciclaje ♻️", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Tus Puntos Totales", fontSize = 16.sp)
                Text("$puntosTotales pts", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { navController.navigate("historial/$userId") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))) {
                Text("📜 Historial")
            }
            Button(onClick = { navController.navigate("ranking") }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA000))) {
                Text("🏆 Ranking")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("¿Qué vas a reciclar hoy?", modifier = Modifier.align(Alignment.Start), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(categorias) { cat ->
                CategoryCard(nombre = cat.nombre, iconRes = cat.iconRes) {
                    navController.navigate("formulario_reciclaje/${userId}/${cat.nombre}")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(nombre: String, iconRes: Int, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.height(130.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = iconRes), contentDescription = nombre, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}