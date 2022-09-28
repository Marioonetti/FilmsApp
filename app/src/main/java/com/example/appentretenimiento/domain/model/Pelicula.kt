package com.example.appentretenimiento.domain.model

import java.time.LocalDate

data class Pelicula(

    val idPelicula: Int,
    val descrip: String?,
    val poster: String,
    val nombre: String,
    val rating: Double?,
    val generos: List<String>?,
    val fechaSalida: LocalDate?,
    val tipo: String,
    var visto : Boolean = false,
    var puntuacionPersonal : Double?

)