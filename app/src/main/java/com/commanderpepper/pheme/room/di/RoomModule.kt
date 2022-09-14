package com.commanderpepper.pheme.room.di

import android.content.Context
import androidx.room.Room
import com.commanderpepper.pheme.room.ArticleDatabase
import com.commanderpepper.pheme.room.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        ArticleDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase) = articleDatabase.articleDao()
}