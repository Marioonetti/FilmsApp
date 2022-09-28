package com.example.appentretenimiento.domain.useclases.series

import com.example.appentretenimiento.data.model.datamappers.toSeriePelicula
import com.example.appentretenimiento.data.repositories.FilmsRepository
import com.example.appentretenimiento.data.repositories.SeriesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllCachedSeries @Inject constructor(private val seriesRepository: SeriesRepository) {

    fun invoke() = seriesRepository.getAllTrendingSeriesCached().map { lista -> lista.map { s -> s.toSeriePelicula() } }

}