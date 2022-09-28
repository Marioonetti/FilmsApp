package com.example.appentretenimiento.data.sources.remote.retrofit

import com.example.appentretenimiento.data.model.internet.busqueda.SearchResponse
import com.example.appentretenimiento.utils.PathRest
import com.example.appentretenimiento.utils.Variables
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MezclaService {

    @GET(PathRest.SEARCH_PATH)
    suspend fun getBySearch(
        @Query(Variables.QUERY) nombre: String,
        @Query(Variables.PAGE) pagina: Int
    ): Response<SearchResponse>

}