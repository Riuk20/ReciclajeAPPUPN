package com.example.reciclajeappupn.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reciclajeappupn.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun FormularioScreen(userId: Int, categoria: String, navController: NavController) {
    var kilos by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registrar $categoria ♻️",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ingresa el peso aproximado para calcular tus puntos",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = kilos,
            onValueChange = { input ->
                // Solo permite números y un punto decimal
                if (input.all { it.isDigit() || it == '.' }) {
                    kilos = input
                }
            },
            label = { Text("Cantidad en Kilos (Kg)") },
            placeholder = { Text("Ej: 2.5") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF388E3C),
                focusedLabelColor = Color(0xFF388E3C)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val peso = kilos.toDoubleOrNull()
                if (peso != null && peso > 0) {
                    scope.launch {
                        try {
                            // Llamada a la API sincronizada con ApiService.kt
                            val response = RetrofitClient.instance.guardarReciclaje(
                                userId,
                                categoria,
                                peso
                            )

                            if (response.status == "success") {
                                // Usamos puntosGanados (nombre corregido en el modelo)
                                Toast.makeText(
                                    context,
                                    "¡Éxito! Ganaste ${response.puntosGanados} puntos",
                                    Toast.LENGTH_LONG
                                ).show()

                                // Regresamos al Dashboard para ver los nuevos puntos
                                navController.popBackStack()
                            } else {
                                // Error enviado por el PHP (ej: error de conexión a BD)
                                Toast.makeText(context, "Servidor: ${response.message}", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            // Error de red o de conversión de JSON
                            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Por favor ingresa un peso válido", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
        ) {
            Text("GUARDAR REGISTRO", fontWeight = FontWeight.Bold)
        }

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Cancelar", color = Color.Gray)
        }
    }
}