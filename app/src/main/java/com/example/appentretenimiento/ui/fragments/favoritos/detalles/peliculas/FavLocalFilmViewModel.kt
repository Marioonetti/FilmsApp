package com.example.appentretenimiento.ui.fragments.favoritos.detalles.peliculas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appentretenimiento.domain.useclases.peliculas.GetFavoriteLocalFilm
import com.example.appentretenimiento.domain.useclases.peliculas.InsertPersonalScore
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavLocalFilmViewModel @Inject constructor(
    private val getFavoriteLocalFilm: GetFavoriteLocalFilm,
    private val insertPersonalScore: InsertPersonalScore
) : ViewModel() {

    private val _uiState:
            MutableStateFlow<FavoriteFilmContract.FavoriteFilmState> by lazy {
        MutableStateFlow(FavoriteFilmContract.FavoriteFilmState())
    }

    val uiState: StateFlow<FavoriteFilmContract.FavoriteFilmState> = _uiState

    private val _uierror = Channel<String>()
    val uierror = _uierror.receiveAsFlow()


    fun controlaEventos(event: FavoriteFilmContract.EventFavoriteFilm) {
        when (event) {
            is FavoriteFilmContract.EventFavoriteFilm.PedirPelicula -> {

                viewModelScope.launch {
                    getFavoriteLocalFilm.invoke(event.id).catch(action = { error ->
                        error.message?.let { _uierror.send(it) }
                    }).collect { result ->
                        _uiState.update {
                            it.copy(
                                pelicula = result
                            )
                        }
                    }

                }
            }
            is FavoriteFilmContract.EventFavoriteFilm.ActualizarPelicula -> {

                viewModelScope.launch {
                    try {
                        event.pelicula?.let {
                            insertPersonalScore.invoke(event.pelicula)
                        }
                    } catch (e: Exception) {
                        e.message?.let { it1 -> _uierror.send(it1) }
                        Log.e(Variables.ERROR_TAG, e.message, e)
                    }


                }

            }
        }


    }


}