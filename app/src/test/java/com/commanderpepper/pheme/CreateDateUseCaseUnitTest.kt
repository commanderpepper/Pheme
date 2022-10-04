package com.commanderpepper.pheme

import com.commanderpepper.pheme.usecase.CreateDateUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateDateUseCaseUnitTest {

    // Represents a October 3rd, a Monday
    private val validTimeStamp = "2022-10-03T19:10:00Z"
    private val formattedTime = "October 3, 2022"
    private val invalidTimeStamp = "2022-10-03"
    private val createDateUseCase = CreateDateUseCase()


    @Test
    fun obtain_day_of_week(){
        val timeOfPublication = createDateUseCase(validTimeStamp)
        Assert.assertTrue(timeOfPublication == formattedTime)
    }

    @Test
    fun use_null_to_obtain_day_of_week(){
        val dayOfWeek = createDateUseCase(null)
        Assert.assertTrue(dayOfWeek == "")
    }

    @Test
    fun use_empty_string_to_obtain_day_of_week(){
        val dayOfWeek = createDateUseCase("")
        Assert.assertTrue(dayOfWeek == "")
    }

    @Test
    fun use_invalid_time_stamp_to_obtain_day_of_week(){
        val dayOfWeek = createDateUseCase(invalidTimeStamp)
        Assert.assertTrue(dayOfWeek == "")
    }
}