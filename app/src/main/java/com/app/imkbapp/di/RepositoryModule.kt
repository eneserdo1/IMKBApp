package com.app.imkbapp.di

import com.app.imkbapp.data.ApiService
import com.app.imkbapp.data.remote.RemoteDataSource
import com.app.imkbapp.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService,
    ): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
    ): RemoteRepository {
        return RemoteRepository(remoteDataSource)
    }
}