package com.commanderpepper.pheme.data.repository

/**
 * This class is used to wrap data from the NewsRepository class
 */
sealed class Status<T> {
    object InProgress: Status<Nothing>()
    class Success<T>(val data: T): Status<T>()
    class Failure(val message: String): Status<Nothing>()
}