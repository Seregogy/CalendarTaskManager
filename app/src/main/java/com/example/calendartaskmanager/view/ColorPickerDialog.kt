package com.example.calendartaskmanager.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Preview(showBackground = true)
@Composable
fun ColorPickerDialog(
    onDismissRequest: () -> Unit = { },
    onColorSelected: (Color) -> Unit = { },
    colors: List<Color> = listOf(
        Color(0xffee9a9a),
        Color(0xfff48fb1),
        Color(0xff80cbc4),
        Color(0xffa5d6a7),
        Color(0xffffcc80),
        Color(0xffffab91),
        Color(0xff81d5fa),
        Color(0xffcf93d9),
        Color(0xffb39ddb)
    )
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Column (
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(40.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Выберите цвет",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500
            )

            LazyVerticalGrid(
                columns = GridCells.FixedSize(40.dp),
                modifier = Modifier
                    .width(150.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(colors) { color ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color)
                            .height(40.dp)
                            .width(40.dp)
                            .clickable {
                                onColorSelected(color)
                                onDismissRequest()
                            }
                    )
                }
            }
        }
    }
}