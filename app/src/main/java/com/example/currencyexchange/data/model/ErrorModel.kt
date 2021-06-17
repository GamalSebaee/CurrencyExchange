package com.example.currencyexchange.data.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("info")
    val info: String,
)