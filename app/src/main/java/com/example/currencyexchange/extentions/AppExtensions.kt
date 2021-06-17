package com.example.currencyexchange.extentions

import com.example.currencyexchange.utils.Constants.Companion.REQUEST_DEFAULT_CURRENCY
import com.example.currencyexchange.utils.Constants.Companion.REQUEST_REFRESH_TIME
import java.math.RoundingMode
import java.text.DecimalFormat


fun Double.roundOffDecimal(): Double {
    return try {
        val df = DecimalFormat("#.#####")
        df.roundingMode = RoundingMode.FLOOR
        df.format(this).toDouble()
    } catch (ex: Exception) {
        this
    }
}

fun String?.getBaseCurrency(): String {
    return this?.replaceFirst(REQUEST_DEFAULT_CURRENCY, "") ?: ""
}

fun String?.changeCurrencyTitle(selectedCurrency: String): String {
    return this?.replaceFirst(REQUEST_DEFAULT_CURRENCY, selectedCurrency) ?: ""
}

fun calculateTimeDifferenceInMinutes(prevTime: Long): Long {
    val currentTime = System.currentTimeMillis()
    val milliSecondsDiff = currentTime - prevTime
    val secondsDiff = milliSecondsDiff / 1000
    return (secondsDiff / 60)
}

fun checkRefreshAvailability(prevTime: Long): Boolean {
    val minutesDiff = calculateTimeDifferenceInMinutes(prevTime)
    return minutesDiff >= REQUEST_REFRESH_TIME
}
