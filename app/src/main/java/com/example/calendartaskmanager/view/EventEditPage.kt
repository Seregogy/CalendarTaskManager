package com.example.calendartaskmanager.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.example.calendartaskmanager.R
import com.example.calendartaskmanager.model.CalendarEvent
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
@Preview(showBackground = true)
fun EditEventPage(
    event: CalendarEvent = CalendarEvent(
        color = Color.Green,
        description = LoremIpsum(words = 5).values.toList().first().toString(),
        place = "Россия"
    ),
    timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"),
    dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd, MMM yy"),
    modifier: Modifier = Modifier,
    onEventAdd: (CalendarEvent) -> Unit = { }
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val eventName = remember { mutableStateOf(event.name) }
    val eventDescription = remember { mutableStateOf(event.description) }
    val eventPlace = remember { mutableStateOf(event.place) }

    val startTime = remember { mutableStateOf(event.eventStart) }
    val endTime = remember { mutableStateOf(event.eventEnd) }

    val notificationEnabled = remember { mutableStateOf(true) }
    val selectedColor = remember { mutableStateOf(event.color) }

    var selectedImagePath by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.let { uri ->
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)

                val file = File(context.applicationContext.filesDir, "img_${bitmap.hashCode()}.jpeg")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, file.outputStream())

                selectedImagePath = file.path
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        ImagePickerBox(
            selectedImagePath,
            launcher
        )

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
            Name(eventName)

            Time(event, dateFormat, timeFormat, startTime, endTime)

            Place(eventPlace)

            Description(eventDescription)

            Parameters(
                notificationEnabled,
                selectedColor
            )

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    event.name = eventName.value
                    event.description = eventDescription.value
                    event.place = eventPlace.value
                    event.eventStart = startTime.value
                    event.eventEnd = endTime.value
                    event.notificationEnabled = notificationEnabled.value
                    event.imagePath = selectedImagePath
                    event.color = selectedColor.value

                    onEventAdd(event)

                    println("${event.name}\n ${event.description}\n ${event.eventStart.format(timeFormat)}\n ${event.eventEnd.format(timeFormat)}\n ${event.color.value}")
                }
            ) {
                Text(
                    text = "Сохранить"
                )
            }
        }
    }
}

@Composable
private fun ImagePickerBox(
    selectedImagePath: String,
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    Box(
        modifier = Modifier
            .clickable {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
            .height(170.dp)
            .wrapContentHeight()
    ) {
        if(selectedImagePath.isNotEmpty()) {
            println(selectedImagePath)

            AsyncImage(
                model = selectedImagePath,
                contentDescription = "selected image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedImagePath.isEmpty()) {
                Text(
                    text = "Добавить картинку",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.W500
                )

                Icon(
                    painter = painterResource(R.drawable.add_icon),
                    contentDescription = "add icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun Name(
    eventName: MutableState<String>
) {
    TextField(
        value = eventName.value,
        onValueChange = {
            if ((eventName.value.count() > 24).not())
                eventName.value = it
        },
        label = {
            Text(
                text = "Название события"
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun Time(
    event: CalendarEvent,
    dateFormat: DateTimeFormatter,
    timeFormat: DateTimeFormatter,
    startTime: MutableState<LocalTime>,
    endTime: MutableState<LocalTime>
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
                TimePickerText(
                    currentDate = event.date,
                    dateFormat = dateFormat,
                    timeFormat = timeFormat,
                    onConfirm = { hour, minute ->
                        startTime.value = LocalTime.of(hour, minute)

                        println(startTime.value.format(timeFormat))
                    }
                )

                Icon(
                    painter = painterResource(R.drawable.arrow_right_icon),
                    contentDescription = "arrowLeft",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

                TimePickerText(
                    currentDate = event.date,
                    dateFormat = dateFormat,
                    timeFormat = timeFormat,
                    onConfirm = { hour, minute ->
                        endTime.value = LocalTime.of(hour, minute)

                        println(endTime.value.format(timeFormat))
                    }
                )
            }
        }
    }
}

@Composable
private fun Place(
    eventPlace: MutableState<String>
) {
    TextField(
        value = eventPlace.value,
        onValueChange = {
            eventPlace.value = it
        },
        label = {
            Text(
                text = "Место"
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun Description(
    eventDescription: MutableState<String>
) {
    TextField(
        value = eventDescription.value,
        onValueChange = {
            eventDescription.value = it
        },
        label = {
            Text(
                text = "Описание"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun Parameters(
    notificationEnabled: MutableState<Boolean>,
    selectedColor: MutableState<Color>
) {
    Column {
        Header("Параметры")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.notifications_icon),
                contentDescription = "idk",
                modifier = Modifier
                    .padding(
                        start = 15.dp,
                        top = 15.dp
                    )
                    .size(24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Включить уведомления"
                    )
                    Checkbox(
                        checked = notificationEnabled.value,
                        onCheckedChange = {
                            notificationEnabled.value = it
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = 10.dp,
                            bottom = 10.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var isDialogEnabled by remember { mutableStateOf(false) }

                    if (isDialogEnabled) {
                        ColorPickerDialog(
                            onDismissRequest = {
                                isDialogEnabled = false
                            },
                            onColorSelected = {
                                selectedColor.value = it
                            }
                        )
                    }

                    Text(
                        "Цвет"
                    )

                    Box (
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(25.dp)
                            .background(selectedColor.value)
                            .clickable {
                                isDialogEnabled = true
                            }
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TimePickerText(
    currentDate: LocalDate = LocalDate.now(),
    dateFormat: DateTimeFormatter,
    timeFormat: DateTimeFormatter,
    onConfirm: (hour: Int, minute: Int) -> Unit = { _, _ -> }
) {
    val time = Calendar.getInstance()

    var showTimePicker by remember { mutableStateOf(false) }
    val localTime = remember { mutableStateOf(LocalTime.now()) }
    val timePickerState = rememberTimePickerState(
        initialHour = time.get(Calendar.HOUR_OF_DAY),
        initialMinute = time.get(Calendar.MINUTE),
        is24Hour = true
    )

    if (showTimePicker) {
        Dialog(
            onDismissRequest = {
                showTimePicker = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = true
            )
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(25.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = "Выберите время",
                    fontWeight = FontWeight.W600
                )

                TimeInput(
                    state = timePickerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Row (
                    modifier = Modifier
                        .wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Button(
                        onClick = {
                            onConfirm(timePickerState.hour, timePickerState.minute)
                            localTime.value = LocalTime.of(timePickerState.hour, timePickerState.minute)

                            showTimePicker = false
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = "Ок"
                        )
                    }

                    TextButton(
                        onClick = {
                            showTimePicker = false
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = "Отмена"
                        )
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(2.dp)
            .clip(RoundedCornerShape(5.dp))
            .clickable {
                showTimePicker = true
            },
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = currentDate.format(dateFormat),
            fontWeight = FontWeight.W600
        )

        Text(
            text = localTime.value.format(timeFormat)
        )
    }
}