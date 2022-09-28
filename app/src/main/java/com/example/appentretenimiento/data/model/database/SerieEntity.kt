package com.example.appentretenimiento.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appentretenimiento.utils.DatabaseConstantes

@Entity(tableName = DatabaseConstantes.SERIES_TABLE)
data class SerieEntity(
    @PrimaryKey
    val id: Int,
    val titulo: String,
    val imagen: String,
    val tipo: String,


    )