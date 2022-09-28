package com.example.appentretenimiento.ui.fragments.busqueda

import com.example.appentretenimiento.domain.model.SeriePeliculaActor

interface BusquedaContract {

    sealed class EventBusqueda {
        data class Buscar(val nombre: String) : EventBusqueda()


    }

    data class BusquedaState(
        val listaBusqueda: List<SeriePeliculaActor>? = emptyList(),
        val loading: Boolean = false,
        val error: String? = null
    )


}