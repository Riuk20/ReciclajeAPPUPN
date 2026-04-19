package com.example.reciclajeappupn.pantallas

import com.google.gson.annotations.SerializedName

// 1. Modelo para la respuesta de puntos del Dashboard
data class PuntosResponse(
    @SerializedName("puntos_totales")
    val puntosTotales: Int
)

// 2. Modelo para los elementos del Ranking
data class RankingItem(
    val nombre: String,
    @SerializedName("puntos_totales")
    val puntosTotales: Int
)

// 3. Modelo para los elementos del Historial
