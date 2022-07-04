package com.commanderpepper.pheme.repository

import kotlin.Result

sealed class ResultOf<T> {
    object Loading: ResultOf<Nothing>()
    class Success<T>(val data: T): ResultOf<T>()
    class Error(val message: String): ResultOf<Nothing>()
}