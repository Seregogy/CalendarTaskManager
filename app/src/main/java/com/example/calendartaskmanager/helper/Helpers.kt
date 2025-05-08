package com.example.calendartaskmanager.helper

import android.graphics.Bitmap
import android.graphics.Color
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