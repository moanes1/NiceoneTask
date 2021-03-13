package com.moanes.niceonetask.util

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class HelperKtTest {

    @Test
    fun `calculateAge with date`() {
        mockkStatic(Calendar::class)

        val birthDate = "02-14-2020"
        val age = "1 years, 1 months, 4 days, 5 hours, 14 minutes, 3 seconds"

        every { getCurrentTime() } returns Date(1615691643000)

        Assert.assertEquals(age, calculateAge(birthDate))
    }
    @Test
    fun `calculateAge with wrong formatted date`() {
        mockkStatic(Calendar::class)

        val birthDate = "12-1-2020"
        val age = "Unknown"

        every { getCurrentTime() } returns Date(1615691643000)

        Assert.assertEquals(age, calculateAge(birthDate))
    }

    @Test
    fun `calculateAge with non date`() {
        mockkStatic(Calendar::class)

        val birthDate = "test string"
        val age = "Unknown"

        every { getCurrentTime() } returns Date(1615691643000)

        Assert.assertEquals(age, calculateAge(birthDate))
    }
}