package com.example.calendartaskmanager.helper

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

//@RequiresApi(Build.VERSION_CODES.Q)
//fun BitmapAvgColor(image: Bitmap): Color {
//    val resultColor = mutableListOf(0, 0, 0)
//    val imageResolution = image.width * image.height
//
//    for (x in 0..<image.width) {
//        for (y in 0..<image.height) {
//            resultColor[0] = (image.getColor(x, y).red() / imageResolution).toInt()
//            resultColor[1] = (image.getColor(x, y).green() / imageResolution).toInt()
//            resultColor[2] = (image.getColor(x, y).blue() / imageResolution).toInt()
//        }
//    }
//
//    return Color(resultColor[0], resultColor[1], resultColor[2], 255)
//}

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