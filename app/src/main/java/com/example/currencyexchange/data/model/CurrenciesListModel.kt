package com.example.currencyexchange.data.model

import com.google.gson.annotations.SerializedName

data class CurrenciesListModel(
    @SerializedName("currencies")
    val currencies: Any? = null,
    @SerializedName("quotes")
    val quotes: Any? = null,
    @SerializedName("source")
    val source: String? = null,
) : BaseModel()