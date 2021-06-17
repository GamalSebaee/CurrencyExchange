package com.example.currencyexchange.data.api

import com.example.currencyexchange.utils.Constants.Companion.BASE_URL_FREE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL_FREE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val apiService: ApiService = getRetrofit().create(ApiService::class.java)
    }
}