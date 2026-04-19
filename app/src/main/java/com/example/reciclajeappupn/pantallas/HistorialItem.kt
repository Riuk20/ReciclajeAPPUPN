package com.example.reciclajeappupn.pantallas

import com.google.gson.annotations.SerializedName

data class HistorialItem(
    @SerializedName("categoria_id")
    val categoriaId: String,

    @SerializedName("cantidad_kg")
    val cantidadKg: Double,

    @SerializedName("puntos_ganados")
    val puntosGanados: Int,

    val fecha: String
)