package com.example.movio.remote

import com.example.movio.data.ktorEngine
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

expect fun getTmdbToken() : String

private const val BASE_URL = "https://api.themoviedb.org"
private const val VERSION = "3"

class MovieApi {

    private val client = HttpClient(ktorEngine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        install(DefaultRequest) {
            val token = getTmdbToken()
            require(token.isNotBlank()) { "No API key" }
            header("Accept", "application/json")
            header("Authorization", "Bearer $token")
            url("$BASE_URL/$VERSION/")
        }
    }

    suspend fun fetchDiscovery(page: Int): HttpResponse = client.get("discover/movie") {
        parameter("page", page)
    }
}

@Serializable
data class DiscoverMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    @Serializable
    data class Result(
        val adult: Boolean,
        val backdrop_path: String?,
        val genre_ids: List<Int>,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String?,
        val release_date: String?,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    )
}