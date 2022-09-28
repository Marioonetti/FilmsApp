package com.example.appentretenimiento.domain.useclases.series

import com.example.appentretenimiento.data.repositories.SeriesRepository
import javax.inject.Inject

class InsertAllCachedSeries @Inject constructor(private val seriesRepository: SeriesRepository) {

    fun invoke()  = seriesRepository.insertAllTrendingSeries()

}