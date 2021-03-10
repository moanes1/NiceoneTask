package com.moanes.datasource.model


import com.google.gson.annotations.SerializedName

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
)