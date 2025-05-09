package com.example.calendartaskmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendartaskmanager.model.CalendarEvent
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun Calendar (
    modifier: Modifier = Modifier,
    selectionChanged: (date: LocalDate, events: List<CalendarEvent>) -> Unit = { _, _ -> },
    sizeChanged: (height: Dp) -> Unit = { },
    selectionPositionChanged: (yPosition: Dp) -> Unit = { },
    localDate: LocalDate = LocalDate.now(),
    calendarEvents: Map<LocalDate, List<CalendarEvent>> = mapOf()
) {
    val selectableState = rememberSelectableCalendarState (
        initialMonth = YearMonth.now(),
        initialSelection = listOf(LocalDate.now()),
        initialSelectionMode = SelectionMode.Single
    )

    val localDensity = LocalDensity.current

    SelectableCalendar (
        calendarState = selectableState,
        modifier = modifier
            .padding(10.dp)
            .onGloballyPositioned { coords ->
                val currentHeight = with(localDensity) {
                    coords.size.height.toDp()
                }

                sizeChanged(currentHeight)
            },
        showAdjacentMonths = true,
        horizontalSwipeEnabled = true,
        weekDaysScrollEnabled = true,
        dayContent = { dayState ->
            val currentEvents = calendarEvents.getOrDefault(dayState.date, listOf())

            DayContentDrawer(
                dayState,
                selectableState,
                localDate,
                selectionChanged,
                selectionPositionChanged,
                currentEvents
            )
        },
        monthHeader = { monthState ->
            Text (
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                text = monthState.currentMonth.month.toString(),
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W600
            )
        },
        daysOfWeekHeader = { days ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text (
                    text = days
                        .first()
                        .name
                        .lowercase()
                        .slice(0..1),
                    modifier = Modifier
                        .padding(start = 4.dp)
                )

                days.slice(1..6).forEach { currentDay ->
                    Text (
                        text = currentDay
                            .name
                            .lowercase()
                            .slice(0..1)
                    )
                }
            }
        },
        firstDayOfWeek = DayOfWeek.of(1)
    )
}

@Composable
private fun DayContentDrawer (
    dayState: DayState<DynamicSelectionState>,
    selectableState: CalendarState<DynamicSelectionState>,
    localDate: LocalDate,
    selectionChanged: (LocalDate, List<CalendarEvent>) -> Unit,
    selectionPositionChanged: (yPosition: Dp) -> Unit,
    calendarEvents: List<CalendarEvent> = listOf()
) {
    var yPosition by remember {
        mutableStateOf(0.dp)
    }

    val isCurrentMonth =
        dayState.date.monthValue == selectableState.monthState.currentMonth.monthValue
    val isCurrentDay =
        dayState.selectionState.selection.any { it.dayOfMonth == dayState.date.dayOfMonth && isCurrentMonth }
    val isToday = (dayState.date.dayOfMonth == localDate.dayOfMonth)
        .and(dayState.date.monthValue == localDate.monthValue)
        .and(dayState.date.year == localDate.year)

    Box (
        modifier = Modifier
            .padding(start = 2.dp, end = 2.dp)
            .height(50.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .onGloballyPositioned { coords ->
                yPosition = coords.positionInRoot().y.dp
            }
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    enabled = isCurrentMonth
                ) {
                    selectableState.selectionState.selection = listOf(dayState.date)

                    selectionChanged(dayState.date, calendarEvents)
                    selectionPositionChanged(yPosition)
                }
                .border(
                    color = if (isCurrentDay)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.background,
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dayState.date.dayOfMonth.toString(),
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .background(
                            color = if (isToday)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    fontWeight = getTextWeightByState(isCurrentDay),
                    fontSize = 12.sp,
                    color = getTextColorByState(isToday, isCurrentDay, dayState, isCurrentMonth),
                    textAlign = TextAlign.Center
                )

                for (event in calendarEvents) {
                    Box(
                        modifier = Modifier
                            .padding(1.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(event.color)
                    )
                }
            }
        }
    }
}

@Composable
private fun getTextWeightByState(isCurrentDay: Boolean) =
    if (isCurrentDay)
        FontWeight.W800
    else
        FontWeight.W500

@Composable
private fun getTextColorByState(
    isToday: Boolean,
    isCurrentDay: Boolean,
    dayState: DayState<DynamicSelectionState>,
    isCurrentMonth: Boolean
) = if (isToday)
        MaterialTheme.colorScheme.onPrimary
    else if (isCurrentDay)
        MaterialTheme.colorScheme.primary
    else if (dayState.date.dayOfWeek.value == 7 && isCurrentMonth)
        Color(0xFFe3665D)
    else if (isCurrentMonth)
        MaterialTheme.colorScheme.onBackground
    else
        MaterialTheme.colorScheme.outline