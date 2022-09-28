package com.example.appentretenimiento.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appentretenimiento.utils.DatabaseConstantes


@Entity(tableName = DatabaseConstantes.PELICULAS_TABLA)
data class PeliculaEntity(
    @PrimaryKey
    val id: Int,
    val titulo: String,
    val imagen: String,
    val tipo: String,
    val resumen: String?,
    val puntuacion: Double?,
    val visto: Boolean,
    val puntuacionPropia: Double?


)