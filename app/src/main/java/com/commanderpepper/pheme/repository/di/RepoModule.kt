package com.commanderpepper.pheme.repository.di

import com.commanderpepper.pheme.repository.remote.NewsRemoteDataSource
import com.commanderpepper.pheme.repository.remote.NewsRemoteDataSourceImpl
import com.commanderpepper.pheme.repository.NewsRepository
import com.commanderpepper.pheme.repository.NewsRepositoryImpl
import com.commanderpepper.pheme.repository.local.NewsLocalDataSource
import com.commanderpepper.pheme.repository.local.NewsLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun bindsNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    fun bindsNewsRemoteDataSource(
        newsRemoteDataSourceImpl: NewsRemoteDataSourceImpl
    ): NewsRemoteDataSource

    @Binds
    fun bindsNewsLocalDataSource(
        newsLocalDataSourceImpl: NewsLocalDataSourceImpl
    ): NewsLocalDataSource
}