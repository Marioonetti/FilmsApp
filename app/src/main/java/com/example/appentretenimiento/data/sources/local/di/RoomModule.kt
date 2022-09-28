package com.example.appentretenimiento.data.sources.local.di

import android.content.Context
import androidx.room.Room
import com.example.appentretenimiento.data.sources.local.*
import com.example.appentretenimiento.utils.Variables
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun obtenerBaseDeDatos(@ApplicationContext context: Context): MoviesRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MoviesRoomDatabase::class.java,
            Variables.DB_NAME
        )
            .fallbackToDestructiveMigrationFrom(3)
            .build()
    }

    @Provides
    fun obtenerPeliculasDAO(database: MoviesRoomDatabase): PeliculasDao {
        return database.peliculasDao()
    }

    @Provides
    fun obtenerSeriesDao(database: MoviesRoomDatabase): SeriesDao {
        return database.seriesDao()
    }

    @Provides
    fun obtenerPeliculasCachedDao(database: MoviesRoomDatabase): PeliculasCachedDao {
        return database.peliculasCachedDao()
    }

    @Provides
    fun obtenerSeriesCachedDao(database: MoviesRoomDatabase): SerieCacheDao {
        return database.seriesCacheDao()
    }


}