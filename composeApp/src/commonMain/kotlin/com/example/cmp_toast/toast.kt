package com.example.cmp_toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.ui.tooling.preview.Preview

object ToastController {
    private val _messages = MutableSharedFlow<ToastMessage>(extraBufferCapacity = 10)
    val messages = _messages.asSharedFlow()

    fun show(message: String, level: ToastLevel = ToastLevel.Info, durationMillis: Long = 2000L) {
        _messages.tryEmit(ToastMessage(message, level, durationMillis))
    }

    fun info(message: String) = show(message, ToastLevel.Info)
    fun warning(message: String) = show(message, ToastLevel.Warning)
    fun error(message: String) = show(message, ToastLevel.Error)
}

enum class ToastLevel {
    Info, Warning, Error
}

data class ToastMessage(
    val text: String,
    val level: ToastLevel = ToastLevel.Info,
    val durationMillis: Long = 2000L
)

@Composable
private fun toastColor(level: ToastLevel): Color = when (level) {
    ToastLevel.Info -> Color(0xFF323232)
    ToastLevel.Warning -> Color(0xFFF6A700)
    ToastLevel.Error -> Color(0xFFD32F2F)
}

@Composable
fun ToastHost(
    modifier: Modifier = Modifier,
    durationMillis: Long = 2000L,
) {
    var currentMessage by remember { mutableStateOf<ToastMessage?>(null) }

    LaunchedEffect(Unit) {
        ToastController.messages.collectLatest { msg ->
            currentMessage = msg
            delay(msg.durationMillis)
            currentMessage = null
        }
    }

    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = currentMessage != null,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
        ) {
            ToastText(msg = currentMessage)
        }
    }
}

@Composable
fun ToastText(msg: ToastMessage?) {
    msg ?: return
    Box(
        modifier = Modifier
            .padding(bottom = 80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(toastColor(msg.level))
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = msg.text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
fun ToastTextPreview() {
    Column {
        ToastText(ToastMessage("Toast Message!"))
        ToastText(ToastMessage("Toast Message!", level = ToastLevel.Info))
        ToastText(ToastMessage("Toast Message!", level = ToastLevel.Warning))
        ToastText(ToastMessage("Toast Message!", level = ToastLevel.Error))
    }
}