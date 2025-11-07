package com.example.cmp_toast

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CmpToast",
        alwaysOnTop = true,
    ) {
        App()
    }
}