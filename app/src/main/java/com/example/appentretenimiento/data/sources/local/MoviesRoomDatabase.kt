package com.example.appentretenimiento.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appentretenimiento.data.model.database.PeliculaEntity
import com.example.appentretenimiento.data.model.database.SerieEntity
import com.example.appentretenimiento.data.model.database.cache.PeliculaCacheEntity
import com.example.appentretenimiento.data.model.database.cache.SerieCacheEntity


@Database(
    entities = [PeliculaEntity::class, SerieEntity::class,
        PeliculaCacheEntity::class, SerieCacheEntity::class],
    version = 9
)
abstract class MoviesRoomDatabase : RoomDatabase() {
    abstract fun peliculasDao(): PeliculasDao

    abstract fun seriesDao(): SeriesDao

    abstract fun peliculasCachedDao(): PeliculasCachedDao

    abstract fun seriesCacheDao(): SerieCacheDao

}