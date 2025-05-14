package com.example.calendartaskmanager.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.calendartaskmanager.R
import com.example.calendartaskmanager.helper.parallaxLayoutModifier
import com.example.calendartaskmanager.model.CalendarEvent
import java.time.format.DateTimeFormatter

@Composable
@Preview(showBackground = true)
fun EventPage(
    event: CalendarEvent = CalendarEvent(
        color = Color.Green,
        description = LoremIpsum(words = 5).values.toList().first().toString(),
        place = "Россия"
    ),
    timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"),
    dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd, MMM yy"),
    modifier: Modifier = Modifier,
    onEdit: (CalendarEvent) -> Unit = { }
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        BackgroundImage(event, scrollState)

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 150.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    start = 25.dp,
                    top = 25.dp,
                    end = 25.dp,
                    bottom = 25.dp
                ),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Header(event, onEdit)

            Time(event, dateFormat, timeFormat)

            Place(event)

            Description(event)

            Parameters(event)
        }
    }
}

@Composable
private fun Parameters(event: CalendarEvent) {
    Column {
        Header("Параметры")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.notifications_icon),
                contentDescription = "idk",
                modifier = Modifier
                    .size(24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Уведомления"
                    )

                    Text(
                        text = if (event.notificationEnabled)
                            "включены"
                        else
                            "отключены",
                        fontWeight = FontWeight.W500
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Цвет"
                    )

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(25.dp)
                            .background(event.color)
                    )
                }
            }
        }
    }
}

@Composable
private fun Description(event: CalendarEvent) {
    if (event.description.isEmpty()) return

    Column {
        Header("Описание")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.description_icon),
                contentDescription = "idk",
                modifier = Modifier
                    .size(24.dp)
            )

            Text(
                text = event.description
            )
        }
    }
}

@Composable
private fun Place(event: CalendarEvent) {
    if (event.place.isEmpty()) return
    
    Column {
        Header("Место")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.place_icon),
                contentDescription = "idk",
                modifier = Modifier
                    .size(24.dp)
            )

            Text(
                text = event.place,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun Time(
    event: CalendarEvent,
    dateFormat: DateTimeFormatter,
    timeFormat: DateTimeFormatter
) {
    Column {
        Header("Время")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(15.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.clock_icon),
                contentDescription = "idk",
                modifier = Modifier
                    .size(24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = event.date.format(dateFormat),
                        fontWeight = FontWeight.W600
                    )

                    Text(
                        text = event.eventStart.format(timeFormat)
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.arrow_right_icon),
                    contentDescription = "arrowLeft",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )


                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = event.date.format(dateFormat),
                        fontWeight = FontWeight.W600
                    )

                    Text(
                        text = event.eventEnd.format(timeFormat)
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    event: CalendarEvent,
    onEdit: (CalendarEvent) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = event.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.W500,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            onClick = {
                onEdit(event)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.edit_icon),
                contentDescription = "edit icon",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Composable
private fun BackgroundImage(
    event: CalendarEvent,
    scrollState: ScrollState
) {
    if (event.imagePath.isNotEmpty()) {
        AsyncImage(
            model = event.imagePath,
            contentScale = ContentScale.Crop,
            contentDescription = "eventImage",
            modifier = Modifier
                .height(170.dp)
                .parallaxLayoutModifier(scrollState, 2),
            alpha = .5f
        )
    }
}