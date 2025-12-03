package com.example.cmp_toast

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import platform.UIKit.UIDevice

class IOSPlatform(
    override val isAndroid: Boolean = false,
    override val isIOS: Boolean = true,
    override val isJVM: Boolean = false,
) : Platform {

    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    @OptIn(ExperimentalComposeUiApi::class)
    override fun toastDialogProperties(): DialogProperties {
        return DialogProperties(
            scrimColor = Color.Transparent,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    }

    override fun showToast(message: String, durationMillis: Long) {

    }
}

actual fun getPlatform(): Platform = IOSPlatform()