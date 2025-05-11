package com.example.calendartaskmanager.helper

import android.annotation.SuppressLint
import android.content.Context
import com.example.calendartaskmanager.model.CalendarEvent
import kotlinx.serialization.json.Json

class CalendarEventJsonDataSaver(
    private val context: Context,
    private val saveName: String = "newSave.json"
) {
    fun save(data: MutableList<CalendarEvent>) {
        val serializer = Json {
            prettyPrint = true
        }

        context.openFileOutput(saveName, Context.MODE_PRIVATE).use {
            it.write(serializer.encodeToString(data).toByteArray())
        }
    }
}

class CalendarEventJsonDataLoader(
    private val context: Context,
    private val saveName: String = "newSave.json"
) {
    @SuppressLint("NewApi")
    fun load(): MutableList<CalendarEvent> {
        context.openFileInput(saveName).use {
            return Json.decodeFromString<MutableList<CalendarEvent>>(it.readAllBytes().toString(Charsets.UTF_8))
        }
    }
}