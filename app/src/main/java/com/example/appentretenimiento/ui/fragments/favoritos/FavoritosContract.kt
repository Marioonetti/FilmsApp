package com.example.appentretenimiento.ui.fragments.favoritos

import com.example.appentretenimiento.domain.model.SeriePelicula

interface FavoritosContract {


    sealed class FavoritosEvent {

        object PedirDatos : FavoritosEvent()
        data class EliminarFav(val seriePelicula: SeriePelicula) : FavoritosEvent()
        object EliminarAllFavs : FavoritosEvent()
    }

    data class FavoritosState(
        val listaFavs: List<SeriePelicula>? = emptyList()
    )

}