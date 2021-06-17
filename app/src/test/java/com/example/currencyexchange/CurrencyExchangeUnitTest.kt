package com.example.currencyexchange

import com.example.currencyexchange.extentions.roundOffDecimal
import com.example.currencyexchange.utils.currencyExchangeFromTo
import org.junit.Assert
import org.junit.Test

class CurrencyExchangeUnitTest {
    @Test
    fun `change from currency to another based on USD correct`() {
        /* this is step from converted currency price based on USD to selected currency
           for example if USDAED == 3.67 and USDEGP == 15.67
               then from AED to EGP = 15.67 / 3.67
               result is = 4.269
               that mean 1 AED = 4.26975 EGP
          */
        val USDAED = 3.67
        val USDEGP = 15.67
        val amount = 1.0
        val value=amount.currencyExchangeFromTo(USDEGP,USDAED).roundOffDecimal()

        // result style (1 AED = 4.26975 EGP)
        Assert.assertEquals(value.roundOffDecimal(), 4.26975, 0.0)
    }

    @Test
    fun `change from currency to another based on USD style2 correct`() {
        /* this is step from converted currency price based on USD to selected currency
           for example if USDAED == 3.67 and USDEGP == 15.67
               then from EGP to AED =3.67  / 15.67
               result is = 0.2342
               that mean 1 EGP = 0.2342 AED
          */
        val USDAED = 3.67
        val USDEGP = 15.67
        val amount = 1.0
        val value=amount.currencyExchangeFromTo(USDAED,USDEGP).roundOffDecimal()
        print("value $value")
        // result style (1 EGP = 0.2342 AED)
        Assert.assertEquals(value.roundOffDecimal(), 0.2342, 0.0)
    }
}