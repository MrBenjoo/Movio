package com.example.movio

actual fun debug(tag: String, message: String) {
    println("$tag: $message")
}