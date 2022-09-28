package com.example.appentretenimiento.data.model.datamappers

import com.example.appentretenimiento.data.model.internet.FilmsSeriesResult
import com.example.appentretenimiento.data.model.internet.busqueda.ResultSearch
import com.example.appentretenimiento.data.model.internet.peliculas.PeliculaResponse
import com.example.appentretenimiento.data.model.internet.personas.ActorResponse
import com.example.appentretenimiento.data.model.internet.series.Season
import com.example.appentretenimiento.data.model.internet.series.TvResponse
import com.example.appentretenimiento.data.model.internet.series.temporadas.Episode
import com.example.appentretenimiento.data.model.internet.series.temporadas.TemporadaResponse
import com.example.appentretenimiento.domain.model.*
import com.example.appentretenimiento.utils.Variables
import java.time.LocalDate

fun FilmsSeriesResult.toSeriePelicula(): SeriePelicula {

    val seriePelicula: SeriePelicula = if (mediaType == Variables.SERIE_TYPE) {
        SeriePelicula(

            id, Variables.RUTA_IMAGENES + posterPath, name, mediaType, voteAverage

        )
    } else {
        SeriePelicula(
            id, Variables.RUTA_IMAGENES + posterPath, originalTitle, mediaType, voteAverage
        )
    }


    return seriePelicula

}

fun PeliculaResponse.toPelicula(): Pelicula = Pelicula(
    id, overview,
    Variables.RUTA_IMAGENES + posterPath, originalTitle, voteAverage, genres.map { it.name },
    LocalDate.parse(releaseDate), Variables.PELICULA_TYPE, false, null
)

fun ResultSearch.toSeriePeliculaActor(): SeriePeliculaActor {

    val seriePeliculaActor: SeriePeliculaActor = if (mediaType == Variables.SERIE_TYPE) {
        SeriePeliculaActor(

            id, Variables.RUTA_IMAGENES + posterPath, name, mediaType, voteAverage

        )
    } else if (mediaType == Variables.PERSON_TYPE) {
        SeriePeliculaActor(
            id, Variables.RUTA_IMAGENES + profilePath, name, mediaType, popularity
        )

    } else {
        SeriePeliculaActor(
            id, Variables.RUTA_IMAGENES + posterPath, originalTitle, mediaType, voteAverage
        )
    }

    return seriePeliculaActor

}

fun Season.toTemporada(): Temporada = Temporada(
    id, seasonNumber, Variables.RUTA_IMAGENES + posterPath, name, overview, null
)

fun Episode.toEpisodio(): Episodio = Episodio(
    id, name, overview, Variables.RUTA_IMAGENES + stillPath
)

fun TemporadaResponse.toTemporada(): Temporada = Temporada(
    idTemporada,
    seasonNumber,
    Variables.RUTA_IMAGENES + posterPath,
    name,
    overview,
    episodes.map { it.toEpisodio() }
)

fun TvResponse.toSerie(): Serie = Serie(
    id,
    overview,
    Variables.RUTA_IMAGENES + posterPath,
    name,
    voteAverage,
    seasons.map { it.toTemporada() },
    Variables.SERIE_TYPE
)

fun ActorResponse.toActor(): Actor = Actor(
    id, name, Variables.RUTA_IMAGENES + profilePath, popularity, biography
)