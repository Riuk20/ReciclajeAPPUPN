package com.example.reciclajeappupn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reciclajeappupn.pantallas.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "login") {

                // 1. Login
                composable("login") {
                    LoginScreen(navController = navController)
                }

                // 2. Registro
                composable("register") {
                    RegisterScreen(navController = navController)
                }

                // 3. Dashboard
                composable(
                    route = "dashboard/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                    DashboardScreen(userId, navController)
                }

                // 4. Historial
                composable(
                    route = "historial/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                    HistorialScreen(userId)
                }

                // 5. Ranking
                composable("ranking") {
                    RankingScreen()
                }

                // 6. Formulario
                composable(
                    route = "formulario_reciclaje/{userId}/{categoria}",
                    arguments = listOf(
                        navArgument("userId") { type = NavType.IntType },
                        navArgument("categoria") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                    val categoria = backStackEntry.arguments?.getString("categoria") ?: ""
                    FormularioScreen(userId, categoria, navController)
                }
            }
        }
    }
}