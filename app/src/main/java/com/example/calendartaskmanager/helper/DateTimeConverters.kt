package com.example.calendartaskmanager.helper

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

fun LocalDate.toUnixTimestamp(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
}

fun Long.fromUnixTimeStampToLocalDate(): LocalDate {
    return Instant.ofEpochSecond(this).atZone(ZoneOffset.UTC).toLocalDate()
}