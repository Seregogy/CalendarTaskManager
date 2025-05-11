package com.example.calendartaskmanager.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
    dataProvider: DataProvider<MutableList<CalendarEvent>>,
    modifier: Modifier = Modifier
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val localDateState = rememberSaveable {
        mutableStateOf(LocalDate.now())
    }

    var localHeight by remember {
        mutableStateOf(0.dp)
    }

    var maxSheetHeight by remember {
        mutableStateOf(0.dp)
    }

    val currentCalendarEvents = remember {
        mutableStateListOf<CalendarEvent>()
    }

    val calendarEvents = remember {
        dataProvider.loadData()
    }

    Calendar(
        selectionChanged = { localDate, events ->
            localDateState.value = localDate
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
        calendarEvents = calendarEvents
    )

    CalendarBottomSheet(
        modifier = modifier,
        date = localDateState.value,
        addEventClicked = { localDate ->
            addEventClicked(localDate)
        },
        eventClicked = {
            eventClicked(it)
        },
        targetSheetHeight = screenHeight - localHeight - 25.dp,
        maxSheetHeight = maxSheetHeight,
        calendarEvents = currentCalendarEvents
    )
}