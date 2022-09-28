package com.example.appentretenimiento.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appentretenimiento.data.model.database.SerieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSerieFav(serieEntity: SerieEntity)

    @Query("select * from series")
    fun getAllFavSeries(): Flow<List<SerieEntity>>

    @Query("select count(id) from series where id = :id")
    suspend fun checkIdSerie(id: Int): Int

    @Query("delete from series where id = :id")
    suspend fun delByidSerie(id: Int)

}