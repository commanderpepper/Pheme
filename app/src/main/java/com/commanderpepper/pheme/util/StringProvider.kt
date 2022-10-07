package com.commanderpepper.pheme.util

import androidx.annotation.StringRes

interface StringProvider {
    fun getString(@StringRes stringResource: Int): String
}