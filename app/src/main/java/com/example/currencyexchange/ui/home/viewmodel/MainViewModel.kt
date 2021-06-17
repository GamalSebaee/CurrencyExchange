package com.example.currencyexchange.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.currencyexchange.data.repository.MainRepository
import com.example.currencyexchange.utils.ResponseResource
import kotlinx.coroutines.Dispatchers

class MainViewModel : ViewModel() {

    private val mainRepository = MainRepository()

    fun getAvailableCurrencies() = liveData(Dispatchers.IO) {
        emit(ResponseResource.loading(data = null))
        try {
            emit(ResponseResource.success(data = mainRepository.getAvailableCurrencies()))
        } catch (exception: Exception) {
            emit(
                ResponseResource.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!"
                )
            )
        }
    }

    fun getCurrenciesValues() = liveData(Dispatchers.IO) {
        emit(ResponseResource.loading(data = null))
        try {
            emit(ResponseResource.success(data = mainRepository.getCurrenciesValues()))
        } catch (exception: Exception) {
            emit(
                ResponseResource.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!"
                )
            )
        }
    }
}