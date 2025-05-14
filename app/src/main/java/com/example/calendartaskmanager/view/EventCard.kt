package com.example.calendartaskmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.calendartaskmanager.model.CalendarEvent
import java.time.format.DateTimeFormatter

@Preview(showBackground = true)
@Composable
fun EventView (
    modifier: Modifier = Modifier,
    event: CalendarEvent = CalendarEvent(
        color = Color(0xffe36e66),
        description = LoremIpsum(15).values.toList().first().toString()
    ),
    timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"),
    backgroundColorDivider: Float = 1.5f,
    onClick: (CalendarEvent) -> Unit = { }
) {
    Card (
        modifier = modifier
            .padding(5.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = CardColors(
            containerColor = Color (
                red = event.color.red / backgroundColorDivider,
                green = event.color.green / backgroundColorDivider,
                blue = event.color.blue / backgroundColorDivider
            ),
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .clickable {
                    onClick(event)
                }
        ) {
            if (event.imagePath.isNotEmpty()) {
                AsyncImage (
                    model = event.imagePath,
                    contentDescription = "event card image",
                    contentScale = ContentScale.Crop,
                    alpha = .3f
                )
            }

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, top = 7.dp, end = 15.dp, bottom = 10.dp)
            ) {
                Text (
                    text = event.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.White,
                    maxLines = 1
                )

                Text(
                    text = "${event.eventStart.format(timeFormat)} - ${event.eventEnd.format(timeFormat)}",
                    fontSize = 12.sp,
                    color = Color.White
                )

                Text(
                    text = event.description,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(event.color),
            )
        }
    }
}