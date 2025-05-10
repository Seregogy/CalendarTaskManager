package com.example.calendartaskmanager.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import com.example.calendartaskmanager.serializator.ColorSerializer
import com.example.calendartaskmanager.serializator.LocalDateSerializer
import com.example.calendartaskmanager.serializator.LocalTimeSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

@Serializable
class CalendarEvent (
    var eventId: Long = Random.nextLong(),
    var name: String = "new event",
    var description: String = "",
    var place: String = "",
    @Serializable(with = LocalDateSerializer::class)
    var date: LocalDate = LocalDate.now(),
    @Serializable(with = LocalTimeSerializer::class)
    var eventStart: LocalTime = LocalTime.now(),
    @Serializable(with = LocalTimeSerializer::class)
    var eventEnd: LocalTime = LocalTime.now(),
    var notificationEnabled: Boolean = false,
    @Serializable(with = ColorSerializer::class)
    var color: Color = Color(0xFFFFFFFF),
    @Transient
    var image: Bitmap? = null
)