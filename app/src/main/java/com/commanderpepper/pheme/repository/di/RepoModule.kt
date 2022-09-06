package com.commanderpepper.pheme.repository.di

import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun bindsNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}