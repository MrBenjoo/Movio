package com.example.movio.remote

actual fun getTmdbToken(): String {
    return System.getProperty("TMDB_TOKEN")
        ?: System.getenv("TMDB_API_TOKEN")
        ?: ""
}