package com.example.appentretenimiento.ui.fragments.favoritos.detalles.peliculas

import com.example.appentretenimiento.domain.model.Pelicula

interface FavoriteFilmContract {

    sealed class EventFavoriteFilm {

        data class PedirPelicula(val id: Int) : EventFavoriteFilm()
        data class ActualizarPelicula(val pelicula: Pelicula?) : EventFavoriteFilm()


    }

    data class FavoriteFilmState(

        val pelicula: Pelicula? = null,
        val error: String? = null
    )


}