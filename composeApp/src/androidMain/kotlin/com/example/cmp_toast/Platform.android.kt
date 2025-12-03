package com.example.cmp_toast

import android.os.Build
import androidx.compose.ui.window.DialogProperties

class AndroidPlatform(
    override val isAndroid: Boolean = true,
    override val isIOS: Boolean = false,
    override val isJVM: Boolean = false,
) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override fun toastDialogProperties(): DialogProperties {
        return DialogProperties(
            decorFitsSystemWindows = false,
            usePlatformDefaultWidth = false
        )
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()