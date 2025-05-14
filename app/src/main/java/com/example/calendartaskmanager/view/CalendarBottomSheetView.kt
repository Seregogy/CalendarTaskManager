package com.example.calendartaskmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.calendartaskmanager.model.CalendarEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CalendarBottomSheet (
    modifier: Modifier = Modifier,
    date: LocalDate = LocalDate.now(),
    addEventClicked: (LocalDate) -> Unit = { },
    eventClicked: (CalendarEvent) -> Unit = { },
    targetSheetHeight: Dp = (LocalConfiguration.current.screenHeightDp / 2).dp,
    maxSheetHeight: Dp = LocalConfiguration.current.screenHeightDp.dp,
    calendarEvents: List<CalendarEvent> = listOf()
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    var bottomSheetHeight by remember {
        mutableStateOf(0.dp)
    }

    BottomSheetScaffold(
        modifier = Modifier
            .height(maxSheetHeight)
            .onGloballyPositioned { coord ->
                bottomSheetHeight = coord.size.height.dp
            },
        sheetContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        scaffoldState = scaffoldState,
        sheetTonalElevation = 10.dp,
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .width(30.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
        },
        sheetPeekHeight = targetSheetHeight,
        sheetContent = {
            Box(
                modifier = Modifier
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp, top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = date.dayOfWeek.toString().lowercase().take(3)
                        )
                    }

                    Column (
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        for (event in calendarEvents) {
                            EventView(
                                event = event
                            ) {
                                eventClicked(it)
                            }
                        }
                    }

                    Button(
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            addEventClicked(date)
                            println(date.format(DateTimeFormatter.ISO_DATE))
                        }
                    ) {
                        Row {
                            Icon(
                                Icons.Filled.Add,
                                "add icon",
                                Modifier
                                    .size(20.dp)
                            )
                            Text(
                                text = "Add event"
                            )
                        }

                    }
                }
            }
        }
    ) { }
}

@Composable
fun DrawEvent(event: CalendarEvent) {
    Card (
        modifier = Modifier
            .padding(5.dp)
            .height(70.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { }
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {

            }
            Text(
                modifier = Modifier
                    .padding(15.dp),
                text = event.name,
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(MaterialTheme.colorScheme.primary),
            )
        }
    }
}