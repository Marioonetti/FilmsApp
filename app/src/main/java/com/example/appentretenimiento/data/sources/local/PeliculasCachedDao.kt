package com.example.appentretenimiento.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appentretenimiento.data.model.database.cache.PeliculaCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PeliculasCachedDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllPeliculasCached(list: List<PeliculaCacheEntity>)

    @Query("select * from peliculasCached")
    fun getAllCachedFilms(): Flow<List<PeliculaCacheEntity>>

    @Query("delete from peliculasCached")
    fun deleteAll()

}