package com.example.appentretenimiento.domain.useclases.peliculas

import com.example.appentretenimiento.data.model.datamappers.toSeriePelicula
import com.example.appentretenimiento.data.repositories.FilmsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllFavFilms @Inject constructor(private val filmsRepository: FilmsRepository) {

    fun invoke() = filmsRepository.getAllFavFilms().map { it -> it.map { it.toSeriePelicula() } }


}