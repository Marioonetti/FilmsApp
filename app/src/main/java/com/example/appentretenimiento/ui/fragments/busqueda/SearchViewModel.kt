package com.example.appentretenimiento.ui.fragments.busqueda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.useclases.mezcla.GetBySearch
import com.example.appentretenimiento.ui.fragments.busqueda.BusquedaContract.BusquedaState
import com.example.appentretenimiento.ui.fragments.busqueda.BusquedaContract.EventBusqueda
import com.example.appentretenimiento.utils.Mensajes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val getBySearch: GetBySearch) : ViewModel() {

    private val _uierror = Channel<String>()
    val uierror = _uierror.receiveAsFlow()


    private val _uiState:
            MutableStateFlow<BusquedaState> by lazy {
        MutableStateFlow(BusquedaState())
    }

    val uiState: StateFlow<BusquedaState> = _uiState


    fun controlaEventos(event: EventBusqueda) {
        when (event) {
            is EventBusqueda.Buscar -> {
                viewModelScope.launch {
                    getBySearch.invoke(event.nombre, 1).catch(action = { error ->
                        _uierror.send(error.message ?: Mensajes.FALLO)
                    }).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update { it.copy(error = result.message, loading = false) }
                            }

                            is NetworkResult.Loading -> {
                                _uiState.update { it.copy(loading = true) }
                            }

                            is NetworkResult.Success -> {
                                _uiState.update {
                                    it.copy(
                                        listaBusqueda = result.data, loading = false
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