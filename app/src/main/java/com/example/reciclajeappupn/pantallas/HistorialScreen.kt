package com.example.reciclajeappupn.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment // Importación vital corregida
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reciclajeappupn.RetrofitClient

@Composable
fun HistorialScreen(userId: Int) {
    // Estado para almacenar la lista que viene del servidor
    var historial by remember { mutableStateOf<List<HistorialItem>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    // Efecto para cargar los datos al entrar a la pantalla
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.instance.obtenerHistorial(userId)
            historial = response
            cargando = false
        } catch (e: Exception) {
            cargando = false
            // Opcional: podrías imprimir el error en consola para debuggear
            // println("Error cargando historial: ${e.message}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mi Historial 📜",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (cargando) {
            // Mostramos un indicador de carga centrado
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF4CAF50))
            }
        } else if (historial.isEmpty()) {
            // Mensaje si no hay datos en la tabla 'registros' para este usuario
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Aún no tienes registros.\n¡Empieza a reciclar!",
                    modifier = Modifier.padding(20.dp),
                    color = Color.Gray
                )
            }
        } else {
            // Lista deslizable de tarjetas
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(historial) { item ->
                    HistorialCard(item)
                }
            }
        }
    }
}

@Composable
fun HistorialCard(item: HistorialItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Usamos los nombres de variables sincronizados con HistorialItem
                Text(
                    text = item.categoriaId,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Fecha: ${item.fecha}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${item.cantidadKg} Kg",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
                Text(
                    text = "+${item.puntosGanados} pts",
                    fontSize = 14.sp,
                    color = Color(0xFFFFA000)
                )
            }
        }
    }
}