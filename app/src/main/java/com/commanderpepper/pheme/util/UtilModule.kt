package com.commanderpepper.pheme.util

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UtilModule {
    @Binds
    fun bindsStringProvider(stringProviderImpl: StringProviderImpl): StringProvider
}