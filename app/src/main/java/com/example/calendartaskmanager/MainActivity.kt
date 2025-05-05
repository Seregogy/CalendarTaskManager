package com.example.calendartaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendartaskmanager.ui.theme.CalendarTaskManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CalendarTaskManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var counter = 0;
    var buttonText by remember { mutableStateOf("Click me") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        Text (
            text = "Hello $name!",
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
        Button (
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onClick = {
                counter++;
                buttonText = "Clicked $counter times"
            }
        ) {
            Text(buttonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalendarTaskManagerTheme {
        Greeting("Android")
    }
}