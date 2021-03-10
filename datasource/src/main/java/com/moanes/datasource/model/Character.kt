package com.moanes.datasource.model


import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.math.abs

data class Character(
    @SerializedName("appearance")
    var appearance: List<Int>,
    @SerializedName("better_call_saul_appearance")
    var betterCallSaulAppearance: List<Any>,
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("category")
    var category: String,
    @SerializedName("char_id")
    var charId: Int,
    @SerializedName("img")
    var img: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("nickname")
    var nickname: String,
    @SerializedName("occupation")
    var occupation: List<String>,
    @SerializedName("portrayed")
    var portrayed: String,
    @SerializedName("status")
    var status: String
) {
    val getLiveAge = flow {
        while (true) {
            val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
            val from = sdf.parse(birthday)
            val to = Calendar.getInstance().time

            val diffInMillies = abs(to.time - from.time)

            val seconds = diffInMillies / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            val months = days / 30
            val years = months / 12
            emit("$years years, ${months % 12} months, ${days % 30} days, ${hours % 24} hours, ${minutes % 60} minutes, ${seconds % 60} seconds")
        }
    }
}