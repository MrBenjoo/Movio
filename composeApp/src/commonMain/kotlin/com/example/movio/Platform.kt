package com.example.movio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform