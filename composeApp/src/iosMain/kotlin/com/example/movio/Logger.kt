package com.example.movio

actual fun debug(tag: String, message: String) {
    // TODO: use correct logging mechanism
    println("DEBUG: [$tag] $message")
}