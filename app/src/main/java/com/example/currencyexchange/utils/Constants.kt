package com.example.currencyexchange.utils

class Constants {
    companion object{
        const val REQUEST_REFRESH_TIME=30
        const val REQUEST_TOKEN = "b5f586d5117f09325b10ff8ff7e0be8d"
        const val REQUEST_DEFAULT_CURRENCY = "USD"

        // http is used for Free Plan and https Used for Paid Plans
        const val BASE_URL_FREE = "http://api.currencylayer.com/"
        fun changeCurrencyName(sentCurrency: String) = REQUEST_DEFAULT_CURRENCY + "" + sentCurrency

    }
}