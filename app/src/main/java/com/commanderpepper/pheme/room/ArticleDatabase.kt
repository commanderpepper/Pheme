package com.commanderpepper.pheme.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.commanderpepper.pheme.room.model.ArticleDAO
import com.commanderpepper.pheme.room.model.ArticleEntity

const val DATABASE_NAME = "article"
private const val DATABASE_VERSION = 1

@Database(entities = [ArticleEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDAO
}