package com.example.appentretenimiento.data.sources.remote

import com.example.appentretenimiento.data.model.BaseApiResponse
import com.example.appentretenimiento.data.model.datamappers.*
import com.example.appentretenimiento.data.model.internet.peliculas.PeliculaResponse
import com.example.appentretenimiento.data.model.internet.personas.ActorResponse
import com.example.appentretenimiento.data.model.internet.series.TvResponse
import com.example.appentretenimiento.data.model.internet.series.temporadas.TemporadaResponse
import com.example.appentretenimiento.data.sources.remote.retrofit.ActoresService
import com.example.appentretenimiento.data.sources.remote.retrofit.FilmsService
import com.example.appentretenimiento.data.sources.remote.retrofit.MezclaService
import com.example.appentretenimiento.data.sources.remote.retrofit.SeriesService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val filmsService: FilmsService,
    private val seriesService: SeriesService,
    private val mezclaService: MezclaService,
    private val actoresService: ActoresService
) : BaseApiResponse() {

//    PELICULAS

    suspend fun getPeliculasTrending() =
        safeApiCall(apiCall = { filmsService.getPeliculasTrending() }, transform = { it ->
            it.filmsSeriesResults.map { it.toSeriePelicula() }
        })

    suspend fun getPeliculaById(id: Int) = safeApiCall(
        apiCall = { filmsService.getPeliculaById(id) },
        transform = PeliculaResponse::toPelicula
    )


//    SERIES

    suspend fun getSeriesTrending() =
        safeApiCall(apiCall = { seriesService.getSeriesTrending() }, transform = { it ->
            it.filmsSeriesResults.map { it.toSeriePelicula() }
        })

    suspend fun getSerieById(id: Int) =
        safeApiCall(apiCall = { seriesService.getSerieById(id) }, transform = TvResponse::toSerie)

    suspend fun getTemporada(idSerie: Int, idTemporada: Int) =
        safeApiCall(
            apiCall = { seriesService.getTemporada(idSerie, idTemporada) },
            transform = TemporadaResponse::toTemporada
        )


    //    ACTORES
    suspend fun getActorById(id: Int) = safeApiCall(
        apiCall = { actoresService.getSerieById(id) },
        transform = ActorResponse::toActor
    )


    //    GENERAL
    suspend fun getBySearch(nombre: String, pagina: Int) =
        safeApiCall(apiCall = { mezclaService.getBySearch(nombre, pagina) }, transform = { it ->
            it.resultSearches.map { it.toSeriePeliculaActor() }
                .sortedBy { it.valoracion }
                .reversed()
        })

}