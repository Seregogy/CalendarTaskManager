package com.example.calendartaskmanager.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.calendartaskmanager.model.CalendarEvent
import java.time.LocalDate

class HardcodeDataProvider (
    context: Context
) : DataProvider<MutableMap<LocalDate, MutableList<CalendarEvent>>>(context) {
    var data: MutableMap<LocalDate, MutableList<CalendarEvent>> = mutableMapOf (
        LocalDate.of(LocalDate.now().year, 1, 1) to mutableListOf (
            CalendarEvent(
                name = "Новый Год",
                date = LocalDate.of(LocalDate.now().year, 1, 1),
                color = Color(0xFF4CAF50)
            ),
            CalendarEvent(
                name = "Новый Год тестовый",
                date = LocalDate.of(LocalDate.now().year, 1, 1),
                color = Color(0xFF2196F3)
            )
        ),

        LocalDate.of(LocalDate.now().year, 1, 7) to mutableListOf(
            CalendarEvent(
                name = "Рождество Христово",
                date = LocalDate.of(LocalDate.now().year, 1, 7),
                color = Color(0xFF2196F3)
            )
        ),

        LocalDate.of(LocalDate.now().year, 2, 23) to mutableListOf(
            CalendarEvent(
                name = "День защитника Отечества",
                date = LocalDate.of(LocalDate.now().year, 2, 23),
                color = Color(0xFFF44336)
            )
        ),

        LocalDate.of(LocalDate.now().year, 3, 8) to mutableListOf(
            CalendarEvent(
                name = "Международный женский день",
                date = LocalDate.of(LocalDate.now().year, 3, 8),
                color = Color(0xFFE91E63)
            )
        ),

        LocalDate.of(LocalDate.now().year, 5, 1) to mutableListOf(
            CalendarEvent(
                name = "Праздник Весны и Труда",
                date = LocalDate.of(LocalDate.now().year, 5, 1),
                color = Color(0xFFFF9800)
            )
        ),

        LocalDate.of(LocalDate.now().year, 5, 9) to mutableListOf(
            CalendarEvent(
                name = "День Победы",
                date = LocalDate.of(LocalDate.now().year, 5, 9),
                color = Color(0xFFF44336),
                description = LoremIpsum(200).values.toList().first().toString(),
                place = "Россия"
            )
        ),

        LocalDate.of(LocalDate.now().year, 6, 12) to mutableListOf(
            CalendarEvent(
                name = "День России",
                date = LocalDate.of(LocalDate.now().year, 6, 12),
                color = Color(0xFF3F51B5)
            )
        ),

        LocalDate.of(LocalDate.now().year, 11, 4) to mutableListOf(
            CalendarEvent(
                name = "День народного единства",
                date = LocalDate.of(LocalDate.now().year, 11, 4),
                color = Color(0xFF673AB7)
            )
        )
    )

    override fun loadData(data: Any?): MutableMap<LocalDate, MutableList<CalendarEvent>> {
        return this.data
    }

    override fun getById(id: Long): Any {
        return data.flatMap { events ->
            events.value.filter { event ->
                event.eventId == id
            }
        }.first()
    }

    override fun add(data: Any) {
        if ((data is CalendarEvent).not()) return

        val event = data as CalendarEvent
        if (this.data.containsKey(event.date))
            this.data[event.date]?.add(event)

        else
            this.data[event.date] = mutableListOf(event)
    }

    override fun update(id: Long, data: Any) {
        if ((data is CalendarEvent).not()) return

        val event = data as CalendarEvent
        val eventIndex = this.data[event.date]?.indexOfFirst {
            it.eventId == event.eventId
        }

        this.data[data.date]!![eventIndex!!] = event
    }

    override fun saveData(data: MutableMap<LocalDate, MutableList<CalendarEvent>>, context: Context) {

    }
}