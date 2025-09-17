package com.example.movio.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual val ktorEngine: HttpClientEngine
    get() = OkHttp.create()

