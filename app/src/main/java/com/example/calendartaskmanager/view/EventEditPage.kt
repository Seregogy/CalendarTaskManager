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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calendartaskmanager.R
import com.example.calendartaskmanager.model.CalendarEvent
import java.time.LocalDate

@Composable
@Preview(showBackground = true)
fun EditEventPage(
    localDate: LocalDate = LocalDate.now(),
    event: CalendarEvent = CalendarEvent(color = Color.Green),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = event.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(20.dp)
                        .height(20.dp)
                        .clip(CircleShape)
                        .background(event.color)
                        .clickable { }
                )
            }

            Box {
                Icon(
                    painter = painterResource(R.drawable.watch_icon),
                    contentDescription = "watch",
                    modifier = Modifier
                        .size(25.dp)
                )
            }
        }
    }
}