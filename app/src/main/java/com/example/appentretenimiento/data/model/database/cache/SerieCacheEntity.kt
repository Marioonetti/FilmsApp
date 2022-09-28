package com.example.appentretenimiento.data.model.database.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appentretenimiento.utils.DatabaseConstantes


@Entity(tableName = DatabaseConstantes.SERIES_CACHED_TABLE)
data class SerieCacheEntity(
    @PrimaryKey
    val id: Int,
    val poster: String?,
    val nombre: String,
    val tipo: String?,
)