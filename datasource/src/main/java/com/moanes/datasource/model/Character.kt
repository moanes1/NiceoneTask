package com.moanes.datasource.model


import com.google.gson.annotations.SerializedName


data class Character(
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("char_id")
    var charId: Int,
    @SerializedName("img")
    var img: String="",
    @SerializedName("name")
    var name: String,
    var liveAge: String="Unknown"
)