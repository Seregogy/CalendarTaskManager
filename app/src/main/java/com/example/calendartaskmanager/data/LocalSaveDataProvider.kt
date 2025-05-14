package com.example.calendartaskmanager.data

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.calendartaskmanager.R
import com.example.calendartaskmanager.helper.CalendarEventJsonDataLoader
import com.example.calendartaskmanager.helper.CalendarEventJsonDataSaver
import com.example.calendartaskmanager.model.CalendarEvent
import java.io.File
import java.time.LocalDate

class LocalSaveDataProvider(
    context: Context,
    private val saveFileName: String = "newSave.json"
) : DataProvider<MutableList<CalendarEvent>>(context) {
    var data: MutableList<CalendarEvent> = mutableListOf(
        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 1, 1),
            name = "Новый Год",
            color = Color(0xFF4CAF50)
        ),
        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 1, 1),
            name = "Новый Год тестовый",
            color = Color(0xFF2196F3)
        ),

        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 1, 7),
            name = "Рождество Христово",
            color = Color(0xFF2196F3)
        ),
        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 2, 23),
            name = "День защитника Отечества",
            color = Color(0xFFF44336)
        ),
        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 3, 8),
            name = "Международный женский день",
            color = Color(0xFFE91E63)
        ),
        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 5, 1),
            name = "Праздник Весны и Труда",
            color = Color(0xFFFF9800)
        ),
        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 5, 9),
            name = "День Победы",
            color = Color(0xFFF44336),
            description = LoremIpsum(200).values.toList().first().toString(),
            place = "Россия"
        ),
        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 6, 12),
            name = "День России",
            color = Color(0xFF3F51B5)
        ),
        CalendarEvent(
            date = LocalDate.of(LocalDate.now().year, 11, 4),
            name = "День народного единства",
            color = Color(0xFF673AB7)
        )
    )

    init {
        val targetFile = File(context.filesDir, saveFileName)

        if (targetFile.exists().not())
            saveData(this.data, context)
        else
            loadData()
    }

    override fun loadData(data: Any?): MutableList<CalendarEvent> {
        this.data = CalendarEventJsonDataLoader(context, saveFileName).load()

        return this.data
    }

    override fun getById(id: Long): Any {
        return data.first { event ->
            event.eventId == id
        }
    }

    override fun add(data: Any) {
        if ((data is CalendarEvent).not()) return
        val event = data as CalendarEvent

        if (this.data.contains(event)) return

        this.data.add(event)

        saveData(this.data, context)
    }

    override fun update(id: Long, data: Any) {
        if ((data is CalendarEvent).not()) return
        val event = data as CalendarEvent

        val index = this.data.indexOfFirst {
            it.eventId == id
        }

        this.data[index] = event

        saveData(this.data, context)
    }

    override fun saveData(
        data: MutableList<CalendarEvent>,
        context: Context
    ) {
        CalendarEventJsonDataSaver(context, saveFileName).save(data)
    }
}