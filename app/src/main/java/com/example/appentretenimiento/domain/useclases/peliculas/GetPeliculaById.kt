package com.example.appentretenimiento.domain.useclases.peliculas

import com.example.appentretenimiento.data.repositories.FilmsRepository
import javax.inject.Inject

class GetPeliculaById @Inject constructor(private val filmsRepository: FilmsRepository) {


    fun invoke(id: Int) = filmsRepository.getPeliculaById(id)

}