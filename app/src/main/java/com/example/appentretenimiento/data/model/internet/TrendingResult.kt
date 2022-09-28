package com.example.appentretenimiento.data.model.internet


import com.google.gson.annotations.SerializedName

data class TrendingResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val filmsSeriesResults: List<FilmsSeriesResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)