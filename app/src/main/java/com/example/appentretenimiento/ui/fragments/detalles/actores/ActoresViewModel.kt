package com.example.appentretenimiento.ui.fragments.detalles.actores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.useclases.actores.GetActorById
import com.example.appentretenimiento.ui.fragments.detalles.actores.ActoresContract.ActorState
import com.example.appentretenimiento.ui.fragments.detalles.actores.ActoresContract.EventActores
import com.example.appentretenimiento.utils.Mensajes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActoresViewModel @Inject constructor(
    private val getActorById: GetActorById
) : ViewModel() {


    private val _uierror = Channel<String>()
    val uierror = _uierror.receiveAsFlow()


    private val _uiState:
            MutableStateFlow<ActorState> by lazy {
        MutableStateFlow(ActorState())
    }

    val uiState: StateFlow<ActorState> = _uiState

    fun controlaEventos(event: EventActores) {
        when (event) {
            is EventActores.PedirActor -> {
                viewModelScope.launch {
                    getActorById.invoke(event.idActor).catch(action = { error ->
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
                                        actor = result.data, loading = false
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