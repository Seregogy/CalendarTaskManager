package com.example.calendartaskmanager.serializer

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ColorSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Color", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Color {
        val colorComponents = decoder.decodeString().split(',').map {
            it.toFloat()
        }

        return Color(
            colorComponents[0],
            colorComponents[1],
            colorComponents[2],
            colorComponents[3]
        )
    }

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeString("${value.red},${value.green},${value.blue},${value.alpha}")
    }
}