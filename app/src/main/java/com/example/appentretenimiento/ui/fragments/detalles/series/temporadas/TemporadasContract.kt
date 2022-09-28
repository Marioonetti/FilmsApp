package com.example.appentretenimiento.ui.fragments.detalles.series.temporadas

import com.example.appentretenimiento.domain.model.Temporada

interface TemporadasContract {

    sealed class TemporadaEvent {
        data class PedirTemporada(val idSerie: Int, val idTemporada: Int) : TemporadaEvent()

    }

    data class TemporadaState(
        val temporada: Temporada? = null,
        val loading: Boolean = false
    )

}