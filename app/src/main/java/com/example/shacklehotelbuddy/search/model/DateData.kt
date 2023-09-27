package com.example.shacklehotelbuddy.search.model

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class DateData(
    val day: Int,
    val month: Int,
    val year: Int
) {

    override fun toString(): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val sdf = SimpleDateFormat("dd / MM / yyyy", Locale.US)
        return sdf.format(calendar)
    }

    companion object {
        fun default(): DateData {
            val calendar = Calendar.getInstance()
            return DateData(
                day = calendar.get(Calendar.DAY_OF_MONTH),
                month = calendar.get(Calendar.MONTH),
                year = calendar.get(Calendar.YEAR)
            )
        }
    }
}



