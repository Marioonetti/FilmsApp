package com.example.appentretenimiento.domain.useclases.series

import com.example.appentretenimiento.data.model.datamappers.toSeriePelicula
import com.example.appentretenimiento.data.repositories.SeriesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllFavSeries @Inject constructor(private val seriesRepository: SeriesRepository) {

    fun invoke() = seriesRepository.getAllFavSeries().map { it.map { it.toSeriePelicula() } }

}