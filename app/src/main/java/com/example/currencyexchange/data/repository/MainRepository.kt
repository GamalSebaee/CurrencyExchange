package com.example.currencyexchange.data.repository

import com.example.currencyexchange.data.api.ApiService
import com.example.currencyexchange.data.api.RetrofitBuilder
import com.example.currencyexchange.utils.Constants.Companion.REQUEST_TOKEN

class MainRepository {
    private val apiService:ApiService = RetrofitBuilder.apiService
    suspend fun getAvailableCurrencies() =
        apiService.getAvailableCurrencies(REQUEST_TOKEN)
    suspend fun getCurrenciesValues() =
        apiService.getCurrenciesValues(REQUEST_TOKEN)

}