package com.example.currencyexchange.utils

fun Double.currencyExchangeFromTo(
    firstCurrencyPrice: Double,
    secondCurrencyPrice: Double
): Double {
    /* this is step from converted currency price based on USD to selected currency
            for example if USDAED == 3.67 and USDEGP == 15.67
                then AEDEGP = 15.67 / 3.67
                result is = 4.269
                that mean 1 AED = 4.269 EGP
           */
    val convertingStep1 = (firstCurrencyPrice / secondCurrencyPrice)
    return (this * convertingStep1)
}