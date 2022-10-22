package com.commanderpepper.pheme.usecase

import com.commanderpepper.pheme.usecase.ConvertISODateToStringUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConvertISODateToStringUseCaseUnitTest {

    // Represents a October 3rd, a Monday
    private val validTimeStamp = "2022-10-03T19:10:00Z"
    private val formattedTime = "October 3, 2022"
    private val invalidTimeStamp = "2022-10-03"
    private val convertISODateToStringUseCase = ConvertISODateToStringUseCase()


    @Test
    fun obtain_day_of_week(){
        val timeOfPublication = convertISODateToStringUseCase(validTimeStamp)
        Assert.assertTrue(timeOfPublication == formattedTime)
    }

    @Test
    fun use_null_to_obtain_day_of_week(){
        val dayOfWeek = convertISODateToStringUseCase(null)
        Assert.assertTrue(dayOfWeek == "")
    }

    @Test
    fun use_empty_string_to_obtain_day_of_week(){
        val dayOfWeek = convertISODateToStringUseCase("")
        Assert.assertTrue(dayOfWeek == "")
    }

    @Test
    fun use_invalid_time_stamp_to_obtain_day_of_week(){
        val dayOfWeek = convertISODateToStringUseCase(invalidTimeStamp)
        Assert.assertTrue(dayOfWeek == "")
    }
}