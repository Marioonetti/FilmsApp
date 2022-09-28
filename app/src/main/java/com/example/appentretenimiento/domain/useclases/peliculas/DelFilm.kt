package com.example.appentretenimiento.domain.useclases.peliculas

import com.example.appentretenimiento.data.repositories.FilmsRepository
import javax.inject.Inject

class DelFilm @Inject constructor(private val filmsRepository: FilmsRepository) {

    suspend fun invoke(id: Int) = filmsRepository.delById(id)

}