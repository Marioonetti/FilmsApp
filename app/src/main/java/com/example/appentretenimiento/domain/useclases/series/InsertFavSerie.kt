package com.example.appentretenimiento.domain.useclases.series

import com.example.appentretenimiento.data.model.datamappers.toSerieEntity
import com.example.appentretenimiento.data.repositories.SeriesRepository
import com.example.appentretenimiento.domain.model.Serie
import javax.inject.Inject

class InsertFavSerie @Inject constructor(private val seriesRepository: SeriesRepository) {

    suspend fun invoke(serie: Serie) = seriesRepository.insertFavSerie(serie.toSerieEntity())

}