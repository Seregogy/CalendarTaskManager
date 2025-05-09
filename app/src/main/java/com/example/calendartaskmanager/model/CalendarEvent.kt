package com.example.calendartaskmanager.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

class CalendarEvent (
    var eventId: Long = Random.nextLong(),
    var name: String = "new event",
    var description: String = "",
    var place: String = "",
    var date: LocalDate = LocalDate.now(),
    var eventStart: LocalTime = LocalTime.now(),
    var eventEnd: LocalTime = LocalTime.now(),
    var notificationEnabled: Boolean = false,
    var color: Color = Color(0xFFFFFFFF),
    var image: Bitmap? = null
)