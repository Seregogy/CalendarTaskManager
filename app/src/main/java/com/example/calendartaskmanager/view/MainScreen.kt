package com.example.calendartaskmanager.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.calendartaskmanager.data.DataProvider
import com.example.calendartaskmanager.model.CalendarEvent
import java.time.LocalDate

@Composable
fun MainScreen(
    addEventClicked: (LocalDate) -> Unit = { },
    eventClicked: (CalendarEvent) -> Unit = { },
    dataProvider: DataProvider<Map<LocalDate, List<CalendarEvent>>>,
    modifier: Modifier = Modifier
) {
    var localDateState by remember {
        mutableStateOf(LocalDate.now())
    }

    var localHeight by remember {
        mutableStateOf(0.dp)
    }

    var maxSheetHeight by remember {
        mutableStateOf(0.dp)
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val currentCalendarEvents = remember {
        mutableListOf<CalendarEvent>()
    }

    Calendar (
        selectionChanged = { localDate, events ->
            localDateState = localDate

            currentCalendarEvents.clear()
            events.forEach {
                currentCalendarEvents.add(it)
            }
        },
        sizeChanged = { height ->
            localHeight = height
        },
        selectionPositionChanged = { yPosition ->
            maxSheetHeight = screenHeight - yPosition
        },
        modifier = modifier,
        calendarEvents = dataProvider.loadData()
    )

    CalendarBottomSheet (
        date = localDateState,
        targetSheetHeight = screenHeight - localHeight - 25.dp,
        maxSheetHeight = maxSheetHeight,
        calendarEvents = currentCalendarEvents,
        addEventClicked = { localDate ->
            addEventClicked(localDate)
        },
        eventClicked = {
            eventClicked(it)
        },
        modifier = modifier
    )
}