package com.example.appentretenimiento.domain.model

data class SeriePelicula(
    val id: Int,
    val poster: String?,
    val nombre: String,
    val tipo: String?,
    val puntuacion: Double?
)