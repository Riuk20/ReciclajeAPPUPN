package com.example.reciclajeappupn

import com.example.reciclajeappupn.pantallas.*
import com.google.gson.annotations.SerializedName
import retrofit2.http.*

interface ApiService {

    // 1. Iniciar Sesión
    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginUsuario(
        @Field("email") email: String,
        @Field("password") pass: String
    ): LoginResponse

    // 2. Registrar Usuario
    @POST("registro.php")
    suspend fun registrarUsuario(
        @Body user: User
    ): GenericResponse

    // 3. Obtener Puntos (Sincronizado con Dashboard)
    @GET("obtener_puntos.php")
    suspend fun obtenerPuntos(
        @Query("user_id") userId: Int
    ): PuntosResponse

    // 4. Guardar Reciclaje (Usando categoria_id como pediste)
    @FormUrlEncoded
    @POST("guardar_reciclaje.php")
    suspend fun guardarReciclaje(
        @Field("usuario_id") userId: Int,
        @Field("categoria_id") categoriaId: String,
        @Field("cantidad_kg") cantidad: Double
    ): RegistroResponse

    // 5. Otros (Historial y Ranking)
    @GET("obtener_historial.php")
    suspend fun obtenerHistorial(@Query("usuario_id") userId: Int): List<HistorialItem>

    @GET("obtener_ranking.php")
    suspend fun obtenerRanking(): List<RankingItem>
}

// --- MODELOS DE RESPUESTA ---

data class LoginResponse(
    val status: String,
    val message: String,
    val user_id: Int? = null
)

data class PuntosResponse(
    val status: String,
    @SerializedName("puntos_totales") // Mapea el nombre de PHP a Kotlin
    val puntosTotales: Int
)

data class RegistroResponse(
    val status: String,
    val message: String,
    @SerializedName("puntos_ganados")
    val puntosGanados: Int? = 0
)

data class GenericResponse(val status: String, val message: String)