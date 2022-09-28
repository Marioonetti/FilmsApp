package com.example.appentretenimiento.ui.fragments.detalles.peliculas

import com.example.appentretenimiento.domain.model.Pelicula

interface DetallesPeliculasContract {

    sealed class DetallesPeliculasEvent {

        data class PedirPelicula(val idPeliBuscar: Int) : DetallesPeliculasEvent()
        data class InsertPelicula(val pelicula: Pelicula) : DetallesPeliculasEvent()
        data class BorrarPelicula(val idBorrar: Int) : DetallesPeliculasEvent()

    }

    data class PeliculaState(
        val pelicula: Pelicula? = null,
        val loading: Boolean = false,
        val guardado: Boolean = false
    )


}