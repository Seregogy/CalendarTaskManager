package com.example.calendartaskmanager

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendartaskmanager.helper.averageColor
import com.example.calendartaskmanager.ui.theme.CalendarTaskManagerTheme
import com.example.calendartaskmanager.view.Calendar
import com.example.calendartaskmanager.view.CalendarBottomSheet
import com.example.calendartaskmanager.view.MainScreen
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CalendarTaskManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(innerPadding)
                    //TestAvgColor(Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun TestAvgColor (
        modifier: Modifier = Modifier
    ) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.arcane)
//        val byteStream = ByteArrayOutputStream()
//        bmp.compress(Bitmap.CompressFormat.PNG, 40, byteStream)
//        val compressedBmp = BitmapFactory.decodeStream(ByteArrayInputStream(byteStream.toByteArray()))

        Box (
            modifier = modifier
                .fillMaxSize()
                .background(color = averageColor(bmp))
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

    @Composable
    @Preview(showBackground = true)
    private fun PreviewFun() {
        CalendarTaskManagerTheme {
            var localDateState by remember {
                mutableStateOf(LocalDate.now())
            }

            Calendar (
                selectionChanged = { localDate, events ->
                    localDateState = localDate
                }
            )

            CalendarBottomSheet (
                date = localDateState,
                targetSheetHeight = (LocalConfiguration.current.screenHeightDp / 2).dp + 60.dp
            )
        }
    }
}