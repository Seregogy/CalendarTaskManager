package com.example.calendartaskmanager.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.calendartaskmanager.model.CalendarEvent
import java.time.LocalDate

class HardcodeDataProvider : DataProvider<Map<LocalDate, List<CalendarEvent>>>() {
    private var data: Map<LocalDate, List<CalendarEvent>> = mapOf (
        LocalDate.of(LocalDate.now().year, 1, 1) to listOf (
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

        LocalDate.of(LocalDate.now().year, 1, 7) to listOf(
            CalendarEvent(
                name = "Рождество Христово",
                date = LocalDate.of(LocalDate.now().year, 1, 7),
                color = Color(0xFF2196F3)
            )
        ),

        LocalDate.of(LocalDate.now().year, 2, 23) to listOf(
            CalendarEvent(
                name = "День защитника Отечества",
                date = LocalDate.of(LocalDate.now().year, 2, 23),
                color = Color(0xFFF44336)
            )
        ),

        LocalDate.of(LocalDate.now().year, 3, 8) to listOf(
            CalendarEvent(
                name = "Международный женский день",
                date = LocalDate.of(LocalDate.now().year, 3, 8),
                color = Color(0xFFE91E63)
            )
        ),

        LocalDate.of(LocalDate.now().year, 5, 1) to listOf(
            CalendarEvent(
                name = "Праздник Весны и Труда",
                date = LocalDate.of(LocalDate.now().year, 5, 1),
                color = Color(0xFFFF9800)
            )
        ),

        LocalDate.of(LocalDate.now().year, 5, 9) to listOf(
            CalendarEvent(
                name = "День Победы",
                date = LocalDate.of(LocalDate.now().year, 5, 9),
                color = Color(0xFFF44336)
            )
        ),

        LocalDate.of(LocalDate.now().year, 6, 12) to listOf(
            CalendarEvent(
                name = "День России",
                date = LocalDate.of(LocalDate.now().year, 6, 12),
                color = Color(0xFF3F51B5)
            )
        ),

        LocalDate.of(LocalDate.now().year, 11, 4) to listOf(
            CalendarEvent(
                name = "День народного единства",
                date = LocalDate.of(LocalDate.now().year, 11, 4),
                color = Color(0xFF673AB7)
            )
        )
    )

    override fun loadData(data: Any?): Map<LocalDate, List<CalendarEvent>> {
        return this.data
    }

    override fun saveData(data: Map<LocalDate, List<CalendarEvent>>, context: Context) {

    }
}