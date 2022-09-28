package com.example.appentretenimiento.data.model.datamappers

import com.example.appentretenimiento.data.model.database.PeliculaEntity
import com.example.appentretenimiento.data.model.database.SerieEntity
import com.example.appentretenimiento.data.model.database.cache.PeliculaCacheEntity
import com.example.appentretenimiento.data.model.database.cache.SerieCacheEntity
import com.example.appentretenimiento.domain.model.Pelicula
import com.example.appentretenimiento.domain.model.Serie
import com.example.appentretenimiento.domain.model.SeriePelicula

fun Pelicula.toPeliculaEntity(): PeliculaEntity = PeliculaEntity(
    idPelicula, nombre, poster, tipo, descrip, rating, visto, puntuacionPersonal

)

fun PeliculaEntity.toSeriePelicula(): SeriePelicula = SeriePelicula(
    id, imagen, titulo, tipo, null
)

fun PeliculaEntity.toPelicula(): Pelicula = Pelicula(
    id, resumen, imagen, titulo, puntuacion, null, null, tipo, visto, puntuacionPropia
)


fun Serie.toSerieEntity(): SerieEntity = SerieEntity(
    idSerie, nombre, poster, tipo
)

fun SerieEntity.toSeriePelicula(): SeriePelicula = SeriePelicula(
    id, imagen, titulo, tipo, null
)

//Cached Peliculas

fun PeliculaCacheEntity.toSeriePelicula(): SeriePelicula = SeriePelicula(
    id, poster, nombre, tipo, null
)

fun SeriePelicula.toPeliculaCacheEntity(): PeliculaCacheEntity = PeliculaCacheEntity(
    id, poster, nombre, tipo
)

//Cached Series

fun SeriePelicula.toSerieCacheEntity(): SerieCacheEntity = SerieCacheEntity(
    id, poster, nombre, tipo
)

fun SerieCacheEntity.toSeriePelicula(): SeriePelicula = SeriePelicula(
    id, poster, nombre, tipo, null
)