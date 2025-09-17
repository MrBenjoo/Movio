package com.example.movio

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.movio.ui.DiscoverScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "My App") {
        DiscoverScreen()
    }
}