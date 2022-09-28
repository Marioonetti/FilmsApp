package com.example.appentretenimiento.data.model.internet.series.temporadas


import com.google.gson.annotations.SerializedName

data class TemporadaResponse(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episodes")
    val episodes: List<Episode>,
    @SerializedName("_id")
    val id: String,
    @SerializedName("id")
    val idTemporada: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("season_number")
    val seasonNumber: Int
)