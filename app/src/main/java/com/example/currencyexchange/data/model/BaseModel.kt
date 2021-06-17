package com.example.currencyexchange.data.model

import com.google.gson.annotations.SerializedName

open class BaseModel {
    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("error")
    val error: ErrorModel? = null
}