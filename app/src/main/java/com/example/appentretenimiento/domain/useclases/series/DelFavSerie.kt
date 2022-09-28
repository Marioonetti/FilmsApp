package com.example.appentretenimiento.domain.useclases.series

import com.example.appentretenimiento.data.repositories.SeriesRepository
import javax.inject.Inject

class DelFavSerie @Inject constructor(private val seriesRepository: SeriesRepository) {

    suspend fun invoke(id: Int) = seriesRepository.delSerieFav(id)

}