package com.example.calendartaskmanager.data

import android.content.Context
import com.example.calendartaskmanager.model.CalendarEvent
import kotlinx.serialization.json.Json

class CalendarEventJsonDataSaver(
    val context: Context,
    val saveName: String = "newSave.json"
) {
    fun save(data: MutableList<CalendarEvent>) {
        context.openFileOutput(saveName, Context.MODE_PRIVATE).use {
            it.write(Json.encodeToString(data).toByteArray())
        }
    }
}