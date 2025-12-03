package com.example.cmp_toast

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties

class JVMPlatform(
    override val isAndroid: Boolean = false,
    override val isIOS: Boolean = false,
    override val isJVM: Boolean = true,
) : Platform {

    override val name: String = "Java ${System.getProperty("java.version")}"

    @OptIn(ExperimentalComposeUiApi::class)
    override fun toastDialogProperties(): DialogProperties {
        return DialogProperties(
            scrimColor = Color.Transparent,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    }
}

actual fun getPlatform(): Platform = JVMPlatform()