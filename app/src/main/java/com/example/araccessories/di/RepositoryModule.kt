package com.example.araccessories.di

import com.example.araccessories.data.dataSource.remoteDataSource.RemoteRepositoryImp
import com.example.araccessories.domain.repositories.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 abstract class RepositoryModule {
    @Binds
    @Singleton
     abstract fun bindMyProductsRepository(
         myRepositoryImpl: RemoteRepositoryImp
     ): RemoteRepository
}