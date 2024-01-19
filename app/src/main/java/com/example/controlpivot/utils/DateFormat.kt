package com.example.controlpivot.utils

import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateFormat {

    companion object {
        fun format(timestamp: Long, format: String): String {
            val dateFormat = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
            return dateFormat.format(Instant.ofEpochMilli(timestamp))
        }

    }

}