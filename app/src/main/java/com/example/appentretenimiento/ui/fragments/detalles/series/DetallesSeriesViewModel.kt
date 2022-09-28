package com.example.appentretenimiento.ui.fragments.detalles.series

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.useclases.series.CheckFavSerie
import com.example.appentretenimiento.domain.useclases.series.DelFavSerie
import com.example.appentretenimiento.domain.useclases.series.GetSerie
import com.example.appentretenimiento.domain.useclases.series.InsertFavSerie
import com.example.appentretenimiento.ui.fragments.detalles.series.SeriesContract.SerieState
import com.example.appentretenimiento.ui.fragments.detalles.series.SeriesContract.SeriesEvent
import com.example.appentretenimiento.utils.Mensajes
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetallesSeriesViewModel @Inject constructor(
    private val getSerie: GetSerie,
    private val delFavSerie: DelFavSerie,
    private val insertFavSerie: InsertFavSerie,
    private val checkFavSerie: CheckFavSerie
) : ViewModel() {


    private val _uiState:
            MutableStateFlow<SerieState> by lazy {
        MutableStateFlow(SerieState())
    }

    val uiState: StateFlow<SerieState> = _uiState


    private val _uierror = Channel<String>()
    val uierror = _uierror.receiveAsFlow()


    fun controlaEventos(event: SeriesEvent) {
        when (event) {

            is SeriesEvent.PedirSerie -> {
                viewModelScope.launch {
                    getSerie.invoke(event.idSerie).catch(action = { error ->
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
                                _uiState.update { it.copy(serie = result.data, loading = false) }
                                try {
                                    result.data?.let { serie ->
                                        _uiState.update {
                                            it.copy(guardado = checkFavSerie.invoke(serie.idSerie) != 0)
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

            is SeriesEvent.BorrarSerie -> {
                viewModelScope.launch {
                    try {
                        delFavSerie.invoke(event.idBorrar)
                        _uiState.update { it.copy(guardado = false) }
                    } catch (e: Exception) {
                        e.message?.let { _uierror.send(it) }
                        Log.e(Variables.ERROR_TAG, e.message, e)
                    }

                }
            }

            is SeriesEvent.InsertSerie -> {

                viewModelScope.launch {
                    try {
                        insertFavSerie.invoke(event.serie)
                        _uiState.update { it.copy(guardado = true) }
                    } catch (e: Exception) {
                        e.message?.let { _uierror.send(it) }
                        Log.e(Variables.ERROR_TAG, e.message, e)
                    }


                }

            }


        }


    }

}