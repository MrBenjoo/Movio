package com.example.movio.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.apache5.Apache5

actual val ktorEngine: HttpClientEngine
    get() = Apache5.create()