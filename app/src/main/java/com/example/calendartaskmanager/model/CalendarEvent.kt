package com.example.calendartaskmanager.model

import androidx.compose.ui.graphics.Color
import com.example.calendartaskmanager.serializer.ColorSerializer
import com.example.calendartaskmanager.serializer.LocalDateSerializer
import com.example.calendartaskmanager.serializer.LocalTimeSerializer
import kotlinx.serialization.Serializable
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
    var imagePath: String = ""
)