package com.example.currencyexchange

import com.example.currencyexchange.extentions.checkRefreshAvailability
import com.example.currencyexchange.utils.Constants
import org.junit.Assert.*
import org.junit.Test

class RefreshAvailabilityUnitTest {

    @Test
    fun `check if time differance bigger than REQUEST_REFRESH_TIME minutes Correct`(){
        val currentTime = System.currentTimeMillis()
        /*Here make last Request Time is 31 minutes
        before current time to make refresh available
        **************
        here minutesDiff : 31 smaller than 30 request update needed
        */
        val lastRequestTime=currentTime-((Constants.REQUEST_REFRESH_TIME+1)*60*1000)
        assertEquals(checkRefreshAvailability(lastRequestTime),true)
    }
    @Test
    fun `check if time differance bigger than REQUEST_REFRESH_TIME minutes Fail`(){
        val currentTime = System.currentTimeMillis()
        /*Here make last Request Time is 31 minutes
        before current time to make refresh available
        **************
        here minutesDiff : 31 smaller than 30 request update needed
        */
        val lastRequestTime=currentTime-((Constants.REQUEST_REFRESH_TIME+1)*60*1000)
        assertEquals(checkRefreshAvailability(lastRequestTime),false)
    }

    @Test
    fun `check if time differance smaller than REQUEST_REFRESH_TIME minutes Correct`(){
        val currentTime = System.currentTimeMillis()
        /*Here make last Request Time is 29 minutes
        before current time to make refresh available
        **************
        here minutesDiff : 29 smaller than 30 no updates needed
        */
        val lastRequestTime=currentTime-((Constants.REQUEST_REFRESH_TIME-1)*60*1000)
        assertEquals(checkRefreshAvailability(lastRequestTime),false)
    }
    @Test
    fun `check if time differance smaller than REQUEST_REFRESH_TIME minutes Fail`(){
        val currentTime = System.currentTimeMillis()
        /*Here make last Request Time is 29 minutes
        before current time to make refresh available
        **************
        here minutesDiff : 29 smaller than 30 no updates needed
        */
        val lastRequestTime=currentTime-((Constants.REQUEST_REFRESH_TIME-1)*60*1000)
        assertEquals(checkRefreshAvailability(lastRequestTime),true)
    }
    @Test
    fun `check if time differance equal REQUEST_REFRESH_TIME minutes correct`(){
        val currentTime = System.currentTimeMillis()
        /*Here make last Request Time is 30 minutes
        before current time to make refresh available
        **************
        here minutesDiff : 30 equal 30 no updates needed
        */
        val lastRequestTime=currentTime-((Constants.REQUEST_REFRESH_TIME)*60*1000)
        assertEquals(checkRefreshAvailability(lastRequestTime),true)
    }
    @Test
    fun `check if time differance equal REQUEST_REFRESH_TIME minutes fail`(){
        val currentTime = System.currentTimeMillis()
        /*Here make last Request Time is 30 minutes
        before current time to make refresh available
        **************
        here minutesDiff : 30 equal 30 no updates needed
        */
        val lastRequestTime=currentTime-((Constants.REQUEST_REFRESH_TIME)*60*1000)
        assertEquals(checkRefreshAvailability(lastRequestTime),false)
    }
}