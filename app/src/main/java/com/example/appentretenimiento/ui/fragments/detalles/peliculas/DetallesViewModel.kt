package com.example.appentretenimiento.ui.fragments.detalles.peliculas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.useclases.peliculas.CheckFilmId
import com.example.appentretenimiento.domain.useclases.peliculas.DelFilm
import com.example.appentretenimiento.domain.useclases.peliculas.GetPeliculaById
import com.example.appentretenimiento.domain.useclases.peliculas.InsertFavFilm
import com.example.appentretenimiento.ui.fragments.detalles.peliculas.DetallesPeliculasContract.DetallesPeliculasEvent
import com.example.appentretenimiento.ui.fragments.detalles.peliculas.DetallesPeliculasContract.PeliculaState
import com.example.appentretenimiento.utils.Mensajes
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesViewModel @Inject constructor(
    private val getPeliculaById: GetPeliculaById,
    private val insertFavFilm: InsertFavFilm,
    private val checkFilmId: CheckFilmId,
    private val delFilm: DelFilm
) : ViewModel() {


    private val _uierror = Channel<String>()
    val uierror = _uierror.receiveAsFlow()


    private val _uiState:
            MutableStateFlow<PeliculaState> by lazy {
        MutableStateFlow(PeliculaState())
    }

    val uiState: StateFlow<PeliculaState> = _uiState


    fun controlaEventos(event: DetallesPeliculasEvent) {
        when (event) {
            is DetallesPeliculasEvent.PedirPelicula -> {

                viewModelScope.launch {

                    getPeliculaById.invoke(event.idPeliBuscar).catch(action = { error ->
                        _uierror.send(error.message ?: Mensajes.FALLO)
                    }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update { it.copy(loading = false) }
                                result.message?.let { _uierror.send(it) }
                            }

                            is NetworkResult.Loading -> {
                                _uiState.update { it.copy(loading = true) }
                            }

                            is NetworkResult.Success -> {
                                _uiState.update {
                                    it.copy(
                                        pelicula = result.data, loading = false
                                    )
                                }

                                try {
                                    result.data?.let { pelicula ->
                                        _uiState.update {
                                            it.copy(
                                                guardado =
                                                checkFilmId.invoke(pelicula.idPelicula) != 0
                                            )
                                        }
                                    }
                                } catch (e: Exception) {
                                    result.message?.let { _uierror.send(it) }
                                    Log.e(Variables.ERROR_TAG, e.message, e)
                                }
                            }

                        }

                    }

                }

            }


            is DetallesPeliculasEvent.BorrarPelicula -> {

                viewModelScope.launch {
                    try {
                        delFilm.invoke(event.idBorrar)
                        _uiState.update { it.copy(guardado = false) }
                    } catch (e: Exception) {
                        e.message?.let { _uierror.send(it) }
                        Log.e(Variables.ERROR_TAG, e.message, e)
                    }
                }

            }

            is DetallesPeliculasEvent.InsertPelicula -> {
                viewModelScope.launch {
                    try {
                        _uiState.update { it.copy(guardado = true) }
                        insertFavFilm.invoke(event.pelicula)
                    } catch (e: Exception) {
                        e.message?.let { _uierror.send(it) }
                        Log.e(Variables.ERROR_TAG, e.message, e)
                    }
                }


            }


        }

    }

}