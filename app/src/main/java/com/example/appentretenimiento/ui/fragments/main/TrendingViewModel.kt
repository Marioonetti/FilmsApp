package com.example.appentretenimiento.ui.fragments.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.useclases.peliculas.GetAllCachedFilms
import com.example.appentretenimiento.domain.useclases.peliculas.InsertAllTrendingCachedMovies
import com.example.appentretenimiento.domain.useclases.series.GetAllCachedSeries
import com.example.appentretenimiento.domain.useclases.series.InsertAllCachedSeries
import com.example.appentretenimiento.utils.Mensajes
import com.example.appentretenimiento.utils.TestInternet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val insertAllTrendingCachedMovies: InsertAllTrendingCachedMovies,
    private val insertAllCachedSeries: InsertAllCachedSeries,
    private val getAllCachedFilms: GetAllCachedFilms,
    private val getAllCachedSeries: GetAllCachedSeries

) : ViewModel() {

    private val _uiState:
            MutableStateFlow<FragmentMainContract.MainFragmentState> by lazy {
        MutableStateFlow(FragmentMainContract.MainFragmentState())
    }
    val uiState: StateFlow<FragmentMainContract.MainFragmentState> = _uiState


    private val _uierror = Channel<String>()
    val uierror = _uierror.receiveAsFlow()


    @RequiresApi(Build.VERSION_CODES.M)
    fun controlaEventos(event: FragmentMainContract.EventMainFragment) {
        when (event) {
            is FragmentMainContract.EventMainFragment.PedirDatos -> {


                if (!TestInternet.hasInternetConnection(event.context)) {

//                        Cargamos Room
                    viewModelScope.launch {
                        getAllCachedFilms.invoke().catch(action = { error ->
                            _uierror.send(error.message ?: Mensajes.FALLO)
                        }).collect { result ->
                            _uiState.update {
                                it.copy(
                                    peliculas = result
                                )
                            }
                        }
                    }
                    viewModelScope.launch {
                        getAllCachedSeries.invoke().catch(action = { error ->
                            _uierror.send(error.message ?: Mensajes.FALLO)
                        }).collect { result ->
                            _uiState.update {
                                it.copy(
                                    series = result
                                )
                            }
                        }
                    }


                } else {
//                      Buscamos en Internet y cacheamos
                    viewModelScope.launch {
                        insertAllTrendingCachedMovies.invoke().catch(action = { error ->
                            _uierror.send(error.message ?: Mensajes.FALLO)
                        }).collect { result ->
                            when (result) {

                                is NetworkResult.Error -> {
                                    result.message?.let { _uierror.send(it) }
                                }

                                is NetworkResult.Loading -> {
                                    _uiState.update { it.copy(loading = true) }
                                }

                                is NetworkResult.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            peliculas = result.data ?: emptyList(),
                                            loading = false
                                        )
                                    }
                                }

                            }

                        }
                    }
                    viewModelScope.launch {
                        insertAllCachedSeries.invoke().catch(action = { error ->
                            _uierror.send(error.message ?: Mensajes.FALLO)
                        }).collect { result ->
                            when (result) {

                                is NetworkResult.Error -> {
                                    result.message?.let { _uierror.send(it) }
                                }

                                is NetworkResult.Loading -> {
                                    _uiState.update { it.copy(loading = true) }
                                }

                                is NetworkResult.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            series = result.data ?: emptyList(), loading = false
                                        )
                                    }
                                }

                            }

                        }

                    }


                }


            }


        }


    }


}