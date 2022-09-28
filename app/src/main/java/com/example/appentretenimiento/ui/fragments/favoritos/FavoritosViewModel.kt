package com.example.appentretenimiento.ui.fragments.favoritos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appentretenimiento.domain.model.SeriePelicula
import com.example.appentretenimiento.domain.useclases.mezcla.GetAllFavFilmSeries
import com.example.appentretenimiento.domain.useclases.peliculas.DelFilm
import com.example.appentretenimiento.domain.useclases.series.DelFavSerie
import com.example.appentretenimiento.utils.Mensajes
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val delFilm: DelFilm,
    private val delFavSerie: DelFavSerie,
    private val getAllFavFilmSeries: GetAllFavFilmSeries
) : ViewModel() {


    private val _uiState:
            MutableStateFlow<FavoritosContract.FavoritosState> by lazy {
        MutableStateFlow(FavoritosContract.FavoritosState())
    }

    val uiState: StateFlow<FavoritosContract.FavoritosState> = _uiState


    private val _uierror = Channel<String>()
    val uierror = _uierror.receiveAsFlow()


    fun controlaEventos(event: FavoritosContract.FavoritosEvent) {

        when (event) {

            is FavoritosContract.FavoritosEvent.PedirDatos -> {

                viewModelScope.launch {
                    getAllFavFilmSeries.invoke().catch(action = { error ->
                        _uierror.send(error.message ?: Mensajes.FALLO)
                    }).collect { result ->
                        _uiState.update {
                            it.copy(
                                listaFavs = result
                            )
                        }
                    }

                }

            }

            is FavoritosContract.FavoritosEvent.EliminarAllFavs -> {
                seleccionados.forEach {
                    viewModelScope.launch {
                        when (it.tipo) {
                            Variables.SERIE_TYPE -> {
                                try {
                                    delFavSerie.invoke(it.id)
                                } catch (e: Exception) {
                                    e.message?.let { it1 -> _uierror.send(it1) }
                                    Log.e(Variables.ERROR_TAG, e.message, e)
                                }
                            }
                            Variables.PELICULA_TYPE -> {
                                try {
                                    delFilm.invoke(it.id)
                                } catch (e: Exception) {
                                    e.message?.let { it1 -> _uierror.send(it1) }
                                    Log.e(Variables.ERROR_TAG, e.message, e)
                                }
                            }

                        }
                    }
                }

            }

            is FavoritosContract.FavoritosEvent.EliminarFav -> {
                viewModelScope.launch {
                    when (event.seriePelicula.tipo) {
                        Variables.SERIE_TYPE -> {
                            try {
                                delFavSerie.invoke(event.seriePelicula.id)
                            } catch (e: Exception) {
                                e.message?.let { it1 -> _uierror.send(it1) }
                                Log.e(Variables.ERROR_TAG, e.message, e)
                            }
                        }
                        Variables.PELICULA_TYPE -> {
                            try {
                                delFilm.invoke(event.seriePelicula.id)
                            } catch (e: Exception) {
                                e.message?.let { it1 -> _uierror.send(it1) }
                                Log.e(Variables.ERROR_TAG, e.message, e)
                            }
                        }

                    }


                }


            }


        }

    }


    //    Funciones y lista del modo seleccion
    private var seleccionados = mutableListOf<SeriePelicula>()

    fun isSeleccionado(seriePelicula: SeriePelicula): Boolean {
        return seleccionados.contains(seriePelicula)
    }

    fun eliminarSeleccionado(seriePelicula: SeriePelicula) {
        seleccionados.remove(seriePelicula)
    }

    fun addSeleccionado(seriePelicula: SeriePelicula) {
        seleccionados.add(seriePelicula)
    }

    fun vaciarLista() {
        seleccionados.clear()
    }

    fun haySeleccionados(): Boolean {
        return seleccionados.size == 0
    }

    fun numSeleccionados(): Int = seleccionados.size


}