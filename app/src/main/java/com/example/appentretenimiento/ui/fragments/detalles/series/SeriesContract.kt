package com.example.appentretenimiento.ui.fragments.detalles.series

import com.example.appentretenimiento.domain.model.Serie

interface SeriesContract {

    sealed class SeriesEvent {

        data class PedirSerie(val idSerie: Int) : SeriesEvent()
        data class InsertSerie(val serie: Serie) : SeriesEvent()
        data class BorrarSerie(val idBorrar: Int) : SeriesEvent()


    }


    data class SerieState(
        val serie: Serie? = null,
        val loading: Boolean = false,
        val guardado: Boolean = false
    )


}