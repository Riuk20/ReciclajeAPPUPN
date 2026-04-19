package com.example.reciclajeappupn.pantallas

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reciclajeappupn.R
import com.example.reciclajeappupn.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Color verde de reciclaje
    val verdeReciclaje = Color(0xFF4CAF50)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. LOGO (Asegúrate de tener ic_logo_reciclaje en drawable)
        Image(
            painter = painterResource(id = R.drawable.ic_logo_reciclaje),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Bienvenido ♻️", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Inicia sesión para continuar", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // 2. CAMPOS CON BORDES REDONDEADOS
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 3. BOTÓN VERDE
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    scope.launch {
                        try {
                            val response = RetrofitClient.instance.loginUsuario(email, password)

                            if (response.status == "success") {
                                // CORRECCIÓN: Usamos navController para ir al Dashboard
                                val id = response.user_id ?: 0
                                navController.navigate("dashboard/$id") {
                                    // Esto evita que el usuario regrese al login al presionar "atrás"
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = verdeReciclaje)
        ) {
            Text("INICIAR SESIÓN", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4. TEXTO PARA IR A REGISTRO
        TextButton(onClick = {
            // CORRECCIÓN: Usamos navController para ir a la pantalla "register"
            navController.navigate("register")
        }) {
            Text("¿No tienes cuenta? Regístrate aquí", color = verdeReciclaje, fontWeight = FontWeight.SemiBold)
        }
    }
}