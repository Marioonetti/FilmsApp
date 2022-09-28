package com.example.appentretenimiento.ui.fragments.main

import android.content.Context
import com.example.appentretenimiento.domain.model.SeriePelicula

interface FragmentMainContract {

    sealed class EventMainFragment {

        data class PedirDatos(val context: Context) : EventMainFragment()

    }

    data class MainFragmentState(
        val peliculas: List<SeriePelicula> = emptyList(),
        val series: List<SeriePelicula> = emptyList(),
        val loading: Boolean = false,
    )

}