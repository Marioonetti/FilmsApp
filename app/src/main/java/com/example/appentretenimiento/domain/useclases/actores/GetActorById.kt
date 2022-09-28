package com.example.appentretenimiento.domain.useclases.actores

import com.example.appentretenimiento.data.repositories.ActorsRepository
import javax.inject.Inject


class GetActorById @Inject constructor(private val actorsRepository: ActorsRepository) {

    fun invoke(id: Int) = actorsRepository.getActorById(id)

}