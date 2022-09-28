package com.example.appentretenimiento.data.repositories

import com.example.appentretenimiento.data.model.database.SerieEntity
import com.example.appentretenimiento.data.model.datamappers.toSerieCacheEntity
import com.example.appentretenimiento.data.sources.local.LocalDataSource
import com.example.appentretenimiento.data.sources.remote.RemoteDataSource
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.model.Serie
import com.example.appentretenimiento.domain.model.SeriePelicula
import com.example.appentretenimiento.domain.model.Temporada
import com.example.appentretenimiento.utils.Variables
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named


@ActivityRetainedScoped
class SeriesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(Variables.NAMED_IO)
    private val dispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource
) {


    fun getSerieById(id: Int): Flow<NetworkResult<Serie>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getSerieById(id))
        }.flowOn(Dispatchers.IO)
    }


    fun getTemporada(idSerie: Int, idTemporada: Int): Flow<NetworkResult<Temporada>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getTemporada(idSerie, idTemporada))
        }.flowOn(Dispatchers.IO)
    }


    fun getAllFavSeries() = localDataSource.getAllFavSeries()

    suspend fun delSerieFav(id: Int) {
        withContext(dispatcher) {
            localDataSource.delByIdSerie(id)
        }
    }

    suspend fun checkSerieFav(id: Int) =
        withContext(dispatcher) {
            localDataSource.checkIdSerie(id)
        }


    suspend fun insertFavSerie(serieEntity: SerieEntity) {
        withContext(dispatcher) {
            localDataSource.insertFavSerie(serieEntity)
        }
    }


    //    Cached Series
    fun insertAllTrendingSeries(): Flow<NetworkResult<List<SeriePelicula>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getSeriesTrending()

            if (result is NetworkResult.Success) {
                result.data?.let { it ->
                    localDataSource.delAllCachedSeries()
                    localDataSource.insertCachedSeries(it.map { it.toSerieCacheEntity() })
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


    fun getAllTrendingSeriesCached() = localDataSource.getAllCachedSeries()


}