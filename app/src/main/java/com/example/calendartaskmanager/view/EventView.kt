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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendartaskmanager.model.CalendarEvent
import java.time.format.DateTimeFormatter

@Preview(showBackground = true)
@Composable
fun EventView (
    modifier: Modifier = Modifier,
    event: CalendarEvent = CalendarEvent(color = Color(0xffe36e66))
) {
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    val divider = 1.5f

    Card (
        modifier = modifier
            .padding(5.dp)
            .height(80.dp)
            .fillMaxWidth(),
        colors = CardColors(
            containerColor = Color (
                red = event.color.red / divider,
                green = event.color.green / divider,
                blue = event.color.blue / divider
            ),
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { }
        ) {
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, top = 7.dp, end = 15.dp, bottom = 10.dp)
            ) {
                Text (
                    text = event.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp
                )

                Row (
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text (
                        text = event.date.format(dateFormatter),
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Right
                    )

                    Text (
                        text = "from ${event.eventStart.format(timeFormatter)} to ${event.eventEnd.format(timeFormatter)}",
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Right
                    )
                }
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