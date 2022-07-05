package com.commanderpepper.pheme.repository

sealed class Status<T> {
    object InProgress: Status<Nothing>()
    class Success<T>(val data: T): Status<T>()
    class Failure(val message: String): Status<Nothing>()
}