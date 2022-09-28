package com.example.appentretenimiento.data.repositories

import com.example.appentretenimiento.data.sources.remote.RemoteDataSource
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.model.Actor
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
class ActorsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(Variables.NAMED_IO)
    private val dispatcher: CoroutineDispatcher
) {

    fun getActorById(id: Int): Flow<NetworkResult<Actor>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getActorById(id))
        }.flowOn(Dispatchers.IO)

    }


}