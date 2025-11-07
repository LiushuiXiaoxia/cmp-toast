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

object ToastKit {
    private val _messages = MutableSharedFlow<ToastMessage>(extraBufferCapacity = 10)
    val messages = _messages.asSharedFlow()

    fun show(
        msg: String,
        level: ToastLevel = ToastLevel.Info,
        position: ToastPosition = ToastPosition.Center,
        durationMillis: Long = 2000L
    ) {
        _messages.tryEmit(ToastMessage(msg, level, position, durationMillis))
    }

    fun info(msg: String, position: ToastPosition = ToastPosition.Center) = show(msg, ToastLevel.Info, position)
    fun warning(msg: String, position: ToastPosition = ToastPosition.Center) = show(msg, ToastLevel.Warning, position)
    fun error(msg: String, position: ToastPosition = ToastPosition.Center) = show(msg, ToastLevel.Error, position)
}

enum class ToastLevel {
    Info, Warning, Error
}


enum class ToastPosition {
    Top,
    Center,
    Bottom
}

data class ToastMessage(
    val msg: String,
    val level: ToastLevel = ToastLevel.Info,
    val position: ToastPosition = ToastPosition.Bottom,
    val durationMillis: Long = 2000L,
)

@Composable
private fun toastColor(level: ToastLevel): Color = when (level) {
    ToastLevel.Info -> Color(0xFF323232)
    ToastLevel.Warning -> Color(0xFFF6A700)
    ToastLevel.Error -> Color(0xFFD32F2F)
}

@Composable
fun ToastHost(modifier: Modifier = Modifier) {
    var currentMessage by remember { mutableStateOf<ToastMessage?>(null) }

    LaunchedEffect(Unit) {
        ToastKit.messages.collectLatest { msg ->
            currentMessage = msg
            delay(msg.durationMillis)
            currentMessage = null
        }
    }
    val alignment = when (currentMessage?.position) {
        ToastPosition.Top -> Alignment.TopCenter
        ToastPosition.Center -> Alignment.Center
        ToastPosition.Bottom -> Alignment.BottomCenter
        else -> Alignment.Center
    }

    Box(
        modifier = modifier.fillMaxSize()
            .padding(top = 80.dp, bottom = 80.dp),
        contentAlignment = alignment,
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
            .padding(30.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(toastColor(msg.level))
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = msg.msg,
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