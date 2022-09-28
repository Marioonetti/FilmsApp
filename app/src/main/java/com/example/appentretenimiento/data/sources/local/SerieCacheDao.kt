package com.example.appentretenimiento.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appentretenimiento.data.model.database.cache.SerieCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SerieCacheDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllSeriesCached(list: List<SerieCacheEntity>)

    @Query("select * from seriesCached")
    fun getAllSeriesCached(): Flow<List<SerieCacheEntity>>

    @Query("delete from seriesCached")
    fun deleteAllSeriesCached()


}