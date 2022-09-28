package com.example.appentretenimiento.domain.useclases.peliculas

import com.example.appentretenimiento.data.model.datamappers.toPelicula
import com.example.appentretenimiento.data.repositories.FilmsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteLocalFilm @Inject constructor(private val filmsRepository: FilmsRepository) {


    fun invoke(id: Int) = filmsRepository.getFavLocalFilm(id).map { it.toPelicula() }

}