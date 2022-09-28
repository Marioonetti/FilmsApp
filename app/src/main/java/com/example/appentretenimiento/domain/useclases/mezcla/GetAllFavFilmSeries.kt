package com.example.appentretenimiento.domain.useclases.mezcla

import com.example.appentretenimiento.data.model.datamappers.toSeriePelicula
import com.example.appentretenimiento.data.repositories.FilmsRepository
import com.example.appentretenimiento.data.repositories.SeriesRepository
import com.example.appentretenimiento.domain.model.SeriePelicula
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllFavFilmSeries @Inject constructor(
    private val filmsRepository: FilmsRepository,
    private val seriesRepository: SeriesRepository
) {


    suspend fun invoke(): Flow<List<SeriePelicula>> {
        val resultadoCombinar = combine(
            filmsRepository.getAllFavFilms(),
            seriesRepository.getAllFavSeries()
        ) { flow1, flow2 ->
//            Creamos lista mutable para poder juntar los dos flows
            val joinedLists: MutableList<SeriePelicula> = mutableListOf()
//            Mapeamos y añadimos a la lista final
            joinedLists.addAll(flow1.map { it.toSeriePelicula() })
            joinedLists.addAll(flow2.map { it.toSeriePelicula() })
            joinedLists
        }.flatMapLatest {
            flow {
                emit(it)
            }
        }
        return resultadoCombinar
    }


}