package com.example.appentretenimiento.data.sources.remote.retrofit

import com.example.appentretenimiento.data.model.internet.TrendingResult
import com.example.appentretenimiento.data.model.internet.peliculas.PeliculaResponse
import com.example.appentretenimiento.utils.PathRest
import com.example.appentretenimiento.utils.Variables
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmsService {


    @GET(PathRest.PELICULAS_POPULARES_URL)
    suspend fun getPeliculasTrending(): Response<TrendingResult>


    @GET(PathRest.PELICULA_PATH)
    suspend fun getPeliculaById(@Path(Variables.ID) id: Int): Response<PeliculaResponse>


}