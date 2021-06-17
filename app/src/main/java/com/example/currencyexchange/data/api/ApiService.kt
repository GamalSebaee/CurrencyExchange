package com.example.currencyexchange.data.api

import com.example.currencyexchange.data.model.CurrenciesListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    suspend fun getAvailableCurrencies(@Query("access_key") accessKey: String):
            CurrenciesListModel

    @GET("live")
    suspend fun getCurrenciesValues(@Query("access_key") accessKey: String):
            CurrenciesListModel
}