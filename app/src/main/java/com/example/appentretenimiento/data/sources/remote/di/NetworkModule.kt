package com.example.appentretenimiento.data.sources.remote.di

import com.example.appentretenimiento.data.sources.remote.ServiceInterceptor
import com.example.appentretenimiento.data.sources.remote.retrofit.ActoresService
import com.example.appentretenimiento.data.sources.remote.retrofit.FilmsService
import com.example.appentretenimiento.data.sources.remote.retrofit.MezclaService
import com.example.appentretenimiento.data.sources.remote.retrofit.SeriesService
import com.example.appentretenimiento.utils.PathRest.BASE_URL
import com.example.appentretenimiento.utils.Variables
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideServiceInterceptor(): ServiceInterceptor {
        return ServiceInterceptor()
    }

    @Singleton
    @Provides
    fun provideHttpClient(serviceInterceptor: ServiceInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(serviceInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideFilmService(retrofit: Retrofit): FilmsService =
        retrofit.create(FilmsService::class.java)

    @Singleton
    @Provides
    fun provideMezclaService(retrofit: Retrofit): MezclaService =
        retrofit.create(MezclaService::class.java)

    @Singleton
    @Provides
    fun provideSerieService(retrofit: Retrofit): SeriesService =
        retrofit.create(SeriesService::class.java)

    @Singleton
    @Provides
    fun provideActorService(retrofit: Retrofit): ActoresService =
        retrofit.create(ActoresService::class.java)


    @Named(Variables.NAMED_IO)
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


}