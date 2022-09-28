package com.example.appentretenimiento.data.sources.remote.retrofit

import com.example.appentretenimiento.data.model.internet.TrendingResult
import com.example.appentretenimiento.data.model.internet.series.TvResponse
import com.example.appentretenimiento.data.model.internet.series.temporadas.TemporadaResponse
import com.example.appentretenimiento.utils.PathRest
import com.example.appentretenimiento.utils.Variables
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesService {

    @GET(PathRest.SERIES_POPULARES_URL)
    suspend fun getSeriesTrending(): Response<TrendingResult>

    @GET(PathRest.SERIE_BY_ID_PATH)
    suspend fun getSerieById(@Path(Variables.ID) id: Int): Response<TvResponse>

    @GET(PathRest.TEMPORADA)
    suspend fun getTemporada(
        @Path(Variables.ID_SERIE) idSerie: Int,
        @Path(Variables.NUM_SEASON) idTemporada: Int
    ): Response<TemporadaResponse>

}