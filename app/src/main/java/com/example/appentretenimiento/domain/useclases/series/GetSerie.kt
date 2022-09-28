package com.example.appentretenimiento.domain.useclases.series

import com.example.appentretenimiento.data.repositories.SeriesRepository
import javax.inject.Inject

class GetSerie @Inject constructor(private val seriesRepository: SeriesRepository) {


    fun invoke(id: Int) = seriesRepository.getSerieById(id)

}