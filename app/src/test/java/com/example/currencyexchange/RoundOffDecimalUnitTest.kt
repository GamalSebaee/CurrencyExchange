package com.example.currencyexchange

import com.example.currencyexchange.extentions.roundOffDecimal
import org.junit.Assert.*
import org.junit.Test

class RoundOffDecimalUnitTest {
    @Test
    fun `use to get five numbers after , to round result  number correct`() {
        /*  Mathematical Operations with Double datatype may return
             many digits after comma so that I used it to get only 5
             number after comma
        */
        val amount=100
        val USDAED = 3.6723456
        val USDEGP = 15.672345
        val value=amount*(USDAED/USDEGP)
        // result = 23.432010972193375
        // I need to display =23.43201
        assertEquals(value.roundOffDecimal(),23.43201,0.0)
    }

    @Test
    fun `use to get five numbers after , to round result  number fail`() {
        /*  Mathematical Operations with Double datatype may return
             many digits after comma so that I used it to get only 5
             number after comma
        */
        val amount=100
        val USDAED = 3.6723456
        val USDEGP = 15.672345
        val value=amount*(USDAED/USDEGP)
        // result = 23.432010972193375
        // I need to display =23.43201
        assertEquals(value.roundOffDecimal(),23.4320111,0.0)
    }
    @Test
    fun `use to get five numbers after , to round result  number corrext`() {
        /*  Mathematical Operations with Double datatype may return
             many digits after comma so that I used it to get only 5
             number after comma
        */
        val amount=100
        val USDAED = 3.0
        val USDEGP = 12.0
        val value=amount*(USDAED/USDEGP)
        // result = 25.0
        // I need to display =25.01
        assertEquals(value.roundOffDecimal(),25.0,25.0)
    }
}