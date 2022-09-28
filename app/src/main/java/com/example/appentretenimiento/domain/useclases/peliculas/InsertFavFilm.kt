package com.example.appentretenimiento.domain.useclases.peliculas

import com.example.appentretenimiento.data.model.datamappers.toPeliculaEntity
import com.example.appentretenimiento.data.repositories.FilmsRepository
import com.example.appentretenimiento.domain.model.Pelicula
import javax.inject.Inject

class InsertFavFilm @Inject constructor(private val filmsRepository: FilmsRepository) {

    suspend fun invoke(pelicula: Pelicula) =
        filmsRepository.insertFavPelicula(pelicula.toPeliculaEntity())

}