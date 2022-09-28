package com.example.appentretenimiento.data.sources.remote.retrofit

import com.example.appentretenimiento.data.model.internet.personas.ActorResponse
import com.example.appentretenimiento.utils.PathRest
import com.example.appentretenimiento.utils.Variables
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActoresService {


    @GET(PathRest.BASE_URL + PathRest.ACTOR_PATH)
    suspend fun getSerieById(@Path(Variables.ID) id: Int): Response<ActorResponse>


}