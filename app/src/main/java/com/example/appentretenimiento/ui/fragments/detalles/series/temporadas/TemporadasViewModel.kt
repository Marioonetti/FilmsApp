package com.example.appentretenimiento.ui.fragments.detalles.series.temporadas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.useclases.series.temporadas.GetTemporada
import com.example.appentretenimiento.ui.fragments.detalles.series.temporadas.TemporadasContract.TemporadaEvent
import com.example.appentretenimiento.ui.fragments.detalles.series.temporadas.TemporadasContract.TemporadaState
import com.example.appentretenimiento.utils.Mensajes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemporadasViewModel @Inject constructor(
    private val getTemporada: GetTemporada
) : ViewModel() {

    private val _uiState:
            MutableStateFlow<TemporadaState> by lazy {
        MutableStateFlow(TemporadaState())
    }

    val uiState: StateFlow<TemporadaState> = _uiState


    private val _uierror = Channel<String>()
    val uierror = _uierror.receiveAsFlow()


    fun controlaEventos(event: TemporadaEvent) {
        when (event) {

            is TemporadaEvent.PedirTemporada -> {

                viewModelScope.launch {
                    getTemporada.invoke(event.idSerie, event.idTemporada).catch(action = { error ->
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
                                        temporada = result.data,
                                        loading = false
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