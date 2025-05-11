package com.example.calendartaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calendartaskmanager.data.HardcodeDataProvider
import com.example.calendartaskmanager.data.LocalSaveDataProvider
import com.example.calendartaskmanager.helper.fromUnixTimeStampToLocalDate
import com.example.calendartaskmanager.helper.toUnixTimestamp
import com.example.calendartaskmanager.model.CalendarEvent
import com.example.calendartaskmanager.ui.theme.CalendarTaskManagerTheme
import com.example.calendartaskmanager.view.Calendar
import com.example.calendartaskmanager.view.CalendarBottomSheet
import com.example.calendartaskmanager.view.EditEventPage
import com.example.calendartaskmanager.view.EventPage
import com.example.calendartaskmanager.view.MainScreen
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CalendarTaskManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val dataProvider = LocalSaveDataProvider(LocalContext.current)

                    NavHost (
                        navController = navController,
                        startDestination = "MainScreen"
                    ) {
                        composable (
                            route = "MainScreen",
                            enterTransition = { slideInHorizontally(tween(400)) },
                            exitTransition = { slideOutHorizontally(tween(400)) }
                        ) {
                            MainScreen (
                                dataProvider = dataProvider,
                                modifier = Modifier
                                    .padding(innerPadding),
                                addEventClicked = { localDate ->
                                    navController.navigate("EditEventPage/${localDate.toUnixTimestamp()}")
                                },
                                eventClicked = { event ->
                                    navController.navigate("EventPage/${event.eventId}")
                                }
                            )
                        }

                        composable (
                            route = "EventPage/{eventId}",
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(400)) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(400)) },
                            arguments = listOf(navArgument("eventId") { type = NavType.LongType })
                        ) {
                            EventPage(
                                event = dataProvider.getById(it.arguments?.getLong("eventId")!!) as CalendarEvent
                            ) { editableEvent ->
                                navController.navigate("UpdateEventPage/${editableEvent.eventId}")
                            }
                        }

                        composable (
                            route = "EditEventPage/{timestamp}",
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(400)) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(400)) },
                            arguments = listOf(
                                navArgument("timestamp") {
                                    type = NavType.LongType
                                }
                            )
                        ) {
                            val timestamp = it.arguments?.getLong("timestamp")!!
                            val localDate = timestamp.fromUnixTimeStampToLocalDate()

                            println(timestamp)
                            println(localDate.format(DateTimeFormatter.ISO_DATE))

                            EditEventPage (
                                event = CalendarEvent(
                                    date = localDate
                                ),
                                modifier = Modifier
                                    .padding(innerPadding)
                            ) { event ->
                                dataProvider.add(event)
                                navController.navigate("MainScreen")
                            }
                        }

                        composable (
                            route = "UpdateEventPage/{eventId}",
                            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(400)) },
                            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(400)) },
                            arguments = listOf(
                                navArgument("eventId") {
                                    type = NavType.LongType
                                }
                            )
                        ) {
                            val eventId = it.arguments?.getLong("eventId")
                            val event = dataProvider.getById(eventId!!) as CalendarEvent

                            EditEventPage (
                                event = event,
                                modifier = Modifier
                                    .padding(innerPadding)
                            ) { changedEvent ->
                                dataProvider.update(changedEvent.eventId, changedEvent)
                                navController.navigate("MainScreen")
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun PreviewFun() {
        CalendarTaskManagerTheme {
            var localDateState by remember {
                mutableStateOf(LocalDate.now())
            }

            Calendar (
                selectionChanged = { localDate, events ->
                    localDateState = localDate
                }
            )

            CalendarBottomSheet (
                date = localDateState,
                targetSheetHeight = (LocalConfiguration.current.screenHeightDp / 2).dp + 60.dp
            )
        }
    }

    fun testSerialization() {
        val event = CalendarEvent(
            name = "Test Event",
            place = "Россия",
            description = "Да просто описание",
            date = LocalDate.of(2023, 12, 31),
            eventStart = LocalTime.of(14, 0),
            eventEnd = LocalTime.of(15, 30)
        )

        try {
            val json = Json.encodeToString(event)
            println("Serialization successful: $json")
        } catch (e: Exception) {
            println("Serialization failed: ${e.message}")
            e.printStackTrace()
        }
    }
}