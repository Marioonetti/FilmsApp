package com.example.appentretenimiento.ui.fragments.detalles.actores

import com.example.appentretenimiento.domain.model.Actor

interface ActoresContract {

    sealed class EventActores {
        data class PedirActor(val idActor: Int) : EventActores()
    }

    data class ActorState(
        val actor: Actor? = null,
        val loading: Boolean = false,
    )


}