package com.commanderpepper.pheme.data.repository.di

import com.commanderpepper.pheme.data.repository.remote.NewsRemoteDataSource
import com.commanderpepper.pheme.data.repository.remote.NewsRemoteDataSourceImpl
import com.commanderpepper.pheme.data.repository.repos.NewsRepository
import com.commanderpepper.pheme.data.repository.repos.NewsRepositoryImpl
import com.commanderpepper.pheme.data.repository.local.NewsLocalDataSource
import com.commanderpepper.pheme.data.repository.local.NewsLocalDataSourceImpl
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