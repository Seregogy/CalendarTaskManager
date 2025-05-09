package com.example.calendartaskmanager.helper

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import com.example.calendartaskmanager.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset


fun averageColor(bitmap: Bitmap): androidx.compose.ui.graphics.Color {
    var red = 0
    var green = 0
    var blue = 0
    val width = bitmap.width
    val height = bitmap.height
    val size = width * height

    for (x in 0 until width) {
        for (y in 0 until height) {
            val pixel = bitmap.getPixel(x, y)
            red += Color.red(pixel)
            green += Color.green(pixel)
            blue += Color.blue(pixel)
        }
    }

    red /= size
    green /= size
    blue /= size

    return androidx.compose.ui.graphics.Color(red, green, blue, 255)
}

fun LocalDate.toUnixTimestamp(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
}

fun Long.fromUnixTimeStampToLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Modifier.parallaxLayoutModifier(scrollState: ScrollState, rate: Int): Modifier {
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val height = if (rate > 0) scrollState.value / rate else scrollState.value
        layout(placeable.width, placeable.height) {
            placeable.place(0, height)
        }
    }
}