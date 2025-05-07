package com.example.calendartaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.calendartaskmanager.ui.theme.CalendarTaskManagerTheme
import com.example.calendartaskmanager.view.Calendar
import com.example.calendartaskmanager.view.CalendarBottomSheet
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

                    var localHeight by remember {
                        mutableStateOf(0.dp)
                    }

                    var maxSheetHeight by remember {
                        mutableStateOf(0.dp)
                    }

                    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

                    Calendar (
                        selectionChanged = { localDate ->
                            localDateState = localDate
                        },
                        sizeChanged = { height ->
                            localHeight = height
                        },
                        selectionPositionChanged = { yPosition ->
                            maxSheetHeight = screenHeight - yPosition
                        },
                        innerPadding = innerPadding
                    )

                    CalendarBottomSheet (
                        date = localDateState,
                        targetSheetHeight = screenHeight - localHeight - 25.dp,
                        maxSheetHeight = maxSheetHeight
                    )
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
                selectionChanged = { localDate ->
                    localDateState = localDate
                }
            )

            CalendarBottomSheet (
                date = localDateState,
                targetSheetHeight = (LocalConfiguration.current.screenHeightDp / 2).dp + 60.dp
            )
        }
    }
}