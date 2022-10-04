package com.commanderpepper.pheme.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject

class CreateDateUseCase @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(dateAsString: String?): String {
        if(dateAsString.isNullOrBlank()){
            return ""
        }

        return try {
            val localDateTime = ZonedDateTime.parse(dateAsString)
            convertToLowercase(localDateTime.month.toString()) + " " + localDateTime.dayOfMonth + ", " + localDateTime.year
        } catch(exception: Exception){
            ""
        }
    }

    private fun convertToLowercase(month: String): String {
        return month.first() + month.drop(1).toLowerCase(Locale.US)
    }
}