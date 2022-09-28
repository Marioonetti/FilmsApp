package com.example.appentretenimiento.domain.model

data class Serie(
    val idSerie: Int,
    val descrip: String?,
    val poster: String,
    val nombre: String,
    val rating: Double?,
    val temporadas: List<Temporada>?,
    val tipo: String


)