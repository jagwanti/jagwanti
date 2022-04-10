@file:JvmName("Utils")
package com.locus.weatherapp

import java.text.SimpleDateFormat
import java.util.*


fun parseDateString(date: String): String {
    val tempDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSSSSS").parse(date)
    val cal = Calendar.getInstance()
    cal.time = tempDate
    return getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK))
}

fun getDayOfWeek(day: Int): String {
    var weekday = ""
    when (day) {
        1 -> weekday = "Sunday"
        2 -> weekday = "Monday"
        3 -> weekday = "Tuesday"
        4 -> weekday = "Wednesday"
        5 -> weekday = "Thursday"
        6 -> weekday = "Friday"
        7 -> weekday = "Saturday"
    }
    return weekday
}
