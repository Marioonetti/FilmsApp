package com.example.appentretenimiento.domain.useclases.peliculas

import com.example.appentretenimiento.data.repositories.FilmsRepository
import javax.inject.Inject

class InsertAllTrendingCachedMovies @Inject constructor(private val filmsRepository: FilmsRepository) {

    fun invoke() = filmsRepository.insertAllTrendingMovies()


}