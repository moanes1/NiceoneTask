package com.moanes.niceonetask.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.abs

fun calculateAge(birthDate: String): String {
    val pattern = Pattern.compile("^(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])-[0-9]{4}\$")
    val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)

    val matcher: Matcher = pattern.matcher(birthDate)
    if (matcher.matches()) {
        val from = sdf.parse(birthDate)
        val to = Calendar.getInstance().time

        val diffInMillies = abs(to.time - from.time)

        val seconds = diffInMillies / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val months = days / 30
        val years = months / 12
        return "$years years, ${months % 12} months, ${days % 30} days, ${hours % 24} hours, ${minutes % 60} minutes, ${seconds % 60} seconds"
    } else {
        return "Unknown"
    }
}