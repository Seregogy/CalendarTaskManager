package com.example.calendartaskmanager.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendartaskmanager.R
import com.example.calendartaskmanager.helper.averageColor
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@Composable
@Preview(showBackground = true)
fun TestAvgColor (
    modifier: Modifier = Modifier
) {
    val bmp = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.arcane)
    val byteStream = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.PNG, 1, byteStream)
    val compressedBmp = BitmapFactory.decodeStream(ByteArrayInputStream(byteStream.toByteArray()))

    Box (
        modifier = modifier
            .fillMaxSize()
            .background(color = averageColor(compressedBmp))
    ) {
        Image (
            bitmap = bmp.asImageBitmap(),
            modifier = Modifier
                .height(350.dp)
                .width(350.dp)
                .clip(RoundedCornerShape(15.dp))
                .align(Alignment.Center),
            contentDescription = "Dua Lipa cover"
        )
    }
}