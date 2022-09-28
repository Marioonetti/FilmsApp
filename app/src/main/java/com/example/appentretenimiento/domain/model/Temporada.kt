package com.example.appentretenimiento.domain.model

data class Temporada(

    val id: Int,
    val numTemporada: Int,
    val poster: String?,
    val nombre: String,
    val resumen: String,
    val episodios: List<Episodio>?

)