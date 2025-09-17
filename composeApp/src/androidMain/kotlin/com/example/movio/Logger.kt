package com.example.movio

import android.util.Log

actual fun debug(tag: String, message: String) {
    Log.d(tag, message)
}