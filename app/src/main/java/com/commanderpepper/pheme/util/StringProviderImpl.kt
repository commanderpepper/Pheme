package com.commanderpepper.pheme.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): StringProvider {
    override fun getString(@StringRes stringResource: Int): String {
        return context.getString(stringResource)
    }
}