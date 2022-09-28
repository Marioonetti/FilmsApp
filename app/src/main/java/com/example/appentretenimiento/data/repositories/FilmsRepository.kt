package com.example.appentretenimiento.data.repositories

import com.example.appentretenimiento.data.model.database.PeliculaEntity
import com.example.appentretenimiento.data.model.datamappers.toPeliculaCacheEntity
import com.example.appentretenimiento.data.sources.local.LocalDataSource
import com.example.appentretenimiento.data.sources.remote.RemoteDataSource
import com.example.appentretenimiento.data.sources.remote.utils.NetworkResult
import com.example.appentretenimiento.domain.model.Pelicula
import com.example.appentretenimiento.domain.model.SeriePelicula
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
class FilmsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @Named(Variables.NAMED_IO)
    private val dispatcher: CoroutineDispatcher,
) {


    fun getPeliculaById(id: Int): Flow<NetworkResult<Pelicula>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getPeliculaById(id))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertFavPelicula(peliculaEntity: PeliculaEntity) =
        withContext(dispatcher) {
            localDataSource.insertFavFilm(peliculaEntity)
        }

    fun getAllFavFilms() = localDataSource.getAllFavFilms()

    fun getAllTrendingMoviesCached() = localDataSource.getAllCachedFilms()

    fun getFavLocalFilm(id: Int) = localDataSource.getFavLocalFilm(id)


    suspend fun checkId(id: Int) =
        withContext(dispatcher) {
            localDataSource.checkId(id)
        }


    suspend fun delById(id: Int) =
        withContext(dispatcher) {
            localDataSource.delById(id)
        }

    suspend fun addPersonalScore(peliculaEntity: PeliculaEntity) =
        withContext(dispatcher) {
            localDataSource.addPersonalScore(peliculaEntity)
        }


    //  ONLINE CACHEADAS

    fun insertAllTrendingMovies(): Flow<NetworkResult<List<SeriePelicula>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = remoteDataSource.getPeliculasTrending()

            if (result is NetworkResult.Success) {
                result.data?.let { it ->
                    localDataSource.delAllCachedFilms()
                    localDataSource.insertCachedFilms(it.map { it.toPeliculaCacheEntity() })
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


}