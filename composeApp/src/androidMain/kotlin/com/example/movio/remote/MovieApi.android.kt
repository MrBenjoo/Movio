package com.example.movio.remote

import com.example.movio.BuildConfig

actual fun getTmdbToken(): String {
    return BuildConfig.TMDB_TOKEN
}