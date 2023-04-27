package com.example.araccessories.di

import com.example.araccessories.data.dataSource.localDataSource.sharedPrefrence.SharedPreference
import com.example.araccessories.data.dataSource.remoteDataSource.ApiService
import com.example.araccessories.data.network.Credentials
import com.example.araccessories.domain.repositories.RemoteRepository
import com.example.araccessories.domain.useCases.ProductsUseCase
import com.example.araccessories.domain.useCases.UserAccountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserLoginUseCase(remoteRepository: RemoteRepository): UserAccountUseCase {
        return UserAccountUseCase(remoteRepository)
    }
    @Provides
    @Singleton
    fun provideProductUseCase(remoteRepository: RemoteRepository): ProductsUseCase {
        return ProductsUseCase(remoteRepository)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    if (chain.request().url.toString().contains("get")) {
                        it.addHeader(
                            "Authorization",
                            "Bearer ${SharedPreference.readStringFromSharedPreference("token", "")}"
                        )
                    }
                }
                    .build())
            }
          .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Credentials.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}