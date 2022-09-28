package com.example.appentretenimiento.data.sources.local

import com.example.appentretenimiento.data.model.database.PeliculaEntity
import com.example.appentretenimiento.data.model.database.SerieEntity
import com.example.appentretenimiento.data.model.database.cache.PeliculaCacheEntity
import com.example.appentretenimiento.data.model.database.cache.SerieCacheEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val peliculasDao: PeliculasDao,
    private val peliculasCachedDao: PeliculasCachedDao,
    private val serieCacheDao: SerieCacheDao,
    private val seriesDao: SeriesDao
) {

//    Peliculas

    suspend fun insertFavFilm(peliculaEntity: PeliculaEntity) =
        peliculasDao.addPeliculaFav(peliculaEntity)

    fun getAllFavFilms() = peliculasDao.getAllFavPeliculas()

    suspend fun checkId(id: Int) = peliculasDao.checkId(id)

    suspend fun delById(id: Int) = peliculasDao.delByid(id)

    fun getFavLocalFilm(id: Int) = peliculasDao.getLocalFavFilm(id)

    suspend fun addPersonalScore(peliculaEntity: PeliculaEntity) =
        peliculasDao.addPuntuacionPersonal(peliculaEntity)


//    Peliculas Cached

    fun getAllCachedFilms() = peliculasCachedDao.getAllCachedFilms()

    fun delAllCachedFilms() = peliculasCachedDao.deleteAll()

    fun insertCachedFilms(list: List<PeliculaCacheEntity>) =
        peliculasCachedDao.addAllPeliculasCached(list)


//    Series Cached

    fun getAllCachedSeries() = serieCacheDao.getAllSeriesCached()

    fun delAllCachedSeries() = serieCacheDao.deleteAllSeriesCached()

    fun insertCachedSeries(list: List<SerieCacheEntity>) = serieCacheDao.addAllSeriesCached(list)


//    Series

    suspend fun insertFavSerie(serieEntity: SerieEntity) = seriesDao.addSerieFav(serieEntity)

    fun getAllFavSeries() = seriesDao.getAllFavSeries()

    suspend fun checkIdSerie(id: Int) = seriesDao.checkIdSerie(id)

    suspend fun delByIdSerie(id: Int) = seriesDao.delByidSerie(id)


}