package com.example.appentretenimiento.data.sources.remote

import com.example.appentretenimiento.utils.Variables
import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter(Variables.API_KEY_PARAM, Variables.API_KEY)
            .addQueryParameter(Variables.API_LANG_PARAM, Variables.LANG)
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}