package com.example.currencyexchange

import com.example.currencyexchange.extentions.changeCurrencyTitle
import com.example.currencyexchange.extentions.getBaseCurrency
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class AppExtensionsUnitTest {

    @Test
    fun `get Currency Title correct`(){
        /* this is function to change title of Currency by replace
        USD with empty to get base code
        I used it in adapter to show text
        1 AED = 15 EGP
        * for example
        * if  currency  USDAED the result = AED
        * */
        val currencyRateTitle="USDEGP"
        val expectedCurrencyRateTitle="EGP"
        assertEquals(currencyRateTitle.getBaseCurrency(),expectedCurrencyRateTitle)
    }
    @Test
    fun `change Currency Title correct`(){
        /* this is function to change title of Currency by replace
        USD with selected currency
        * for example
        * if selected currency  AED we need to change
         list rates from USDEGP to AEDEGP
        * */
        val selectedCurrency="AED"
        val currencyRateTitle="USDEGP"
        val expectedCurrencyRateTitle="AEDEGP"
        assertEquals(currencyRateTitle.changeCurrencyTitle(selectedCurrency),expectedCurrencyRateTitle)
    }
}