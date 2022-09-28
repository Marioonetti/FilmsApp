package com.example.appentretenimiento.domain.useclases.peliculas

import com.example.appentretenimiento.data.model.datamappers.toSeriePelicula
import com.example.appentretenimiento.data.repositories.FilmsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllCachedFilms @Inject constructor(private val filmsRepository: FilmsRepository) {

    fun invoke() = filmsRepository.getAllTrendingMoviesCached().map { lista -> lista.map { p -> p.toSeriePelicula() } }

}