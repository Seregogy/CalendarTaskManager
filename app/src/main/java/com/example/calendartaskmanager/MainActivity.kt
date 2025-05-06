package com.example.calendartaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendartaskmanager.ui.theme.CalendarTaskManagerTheme
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CalendarTaskManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var localDateState by remember {
                        mutableStateOf(LocalDate.now())
                    }

                    Calendar (
                        selectionChanged = { localDate ->
                            localDateState = localDate
                        },
                        innerPadding = innerPadding
                    )

                    CalendarBottomSheet (
                        date = localDateState
                    )
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun Calendar (
        selectionChanged: @Composable (LocalDate) -> Unit = { },
        innerPadding: PaddingValues = PaddingValues(),
    ) {
        val selectableState = rememberSelectableCalendarState (
            initialMonth = YearMonth.now(),
            initialSelection = listOf(LocalDate.now()),
            initialSelectionMode = SelectionMode.Single
        )

        SelectableCalendar (
            calendarState = selectableState,
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp),
            showAdjacentMonths = true,
            horizontalSwipeEnabled = true,
            weekDaysScrollEnabled = true,
            dayContent = { dayState ->
                val isCurrentMonth = dayState.date.monthValue == selectableState.monthState.currentMonth.monthValue

                TextButton (
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp),
                    shape = RoundedCornerShape(50),
                    enabled = isCurrentMonth,
                    border = BorderStroke(
                        color = if (dayState.selectionState.selection.any { it.dayOfMonth == dayState.date.dayOfMonth && isCurrentMonth })
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.background,
                        width = 2.dp
                    ),
                    onClick = {
                        selectableState.selectionState.selection = listOf(dayState.date)
                    }
                ) {
                    Text (
                        text = dayState.date.dayOfMonth.toString(),
                        color = if (isCurrentMonth)
                                    MaterialTheme.colorScheme.onBackground
                                else
                                    MaterialTheme.colorScheme.outline
                    )
                }
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
    @Preview(showBackground = true)
    private fun CalendarBottomSheet (
        date: LocalDate = LocalDate.now()
    ) {

    }
}
