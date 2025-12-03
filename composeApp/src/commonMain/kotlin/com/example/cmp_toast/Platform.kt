package com.example.cmp_toast

import androidx.compose.ui.window.DialogProperties

interface Platform {

    val isAndroid: Boolean
    val isIOS: Boolean
    val isJVM: Boolean

    val name: String

    fun toastDialogProperties(): DialogProperties
}

expect fun getPlatform(): Platform