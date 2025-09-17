package com.example.movio.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual val ktorEngine: HttpClientEngine
    get() = Darwin.create()