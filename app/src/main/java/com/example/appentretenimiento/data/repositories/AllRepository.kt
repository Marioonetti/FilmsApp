package com.example.appentretenimiento.data.repositories

import com.example.appentretenimiento.data.sources.remote.RemoteDataSource
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.model.SeriePeliculaActor
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named


@ActivityRetainedScoped
class AllRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(Variables.NAMED_IO)
    private val dispatcher: CoroutineDispatcher
) {


    fun getBySearch(nombre: String, pagina: Int): Flow<NetworkResult<List<SeriePeliculaActor>>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getBySearch(nombre, pagina))
        }.flowOn(Dispatchers.IO)
    }
}