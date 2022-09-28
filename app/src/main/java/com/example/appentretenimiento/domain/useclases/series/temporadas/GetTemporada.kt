package com.example.appentretenimiento.domain.useclases.series.temporadas

import com.example.appentretenimiento.data.repositories.SeriesRepository
import javax.inject.Inject

class GetTemporada @Inject constructor(private val seriesRepository: SeriesRepository) {

    fun invoke(idSerie: Int, idTemporada: Int) =
        seriesRepository.getTemporada(idSerie, idTemporada)

}