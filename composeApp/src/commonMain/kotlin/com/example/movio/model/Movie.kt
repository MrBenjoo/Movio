package com.example.movio.model

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val poster: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) {

    fun getPosterUrl(size: String = "w500"): String? {
        if (posterPath.isBlank()) return null
        val clean = if (posterPath.startsWith("/")) posterPath else "/$posterPath"
        return "https://image.tmdb.org/t/p/$size$clean"
    }
}