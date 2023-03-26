package com.example.araccessories.data.network

import com.example.araccessories.data.dataSource.remoteDataSource.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private val retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
        Retrofit.Builder().baseUrl(Credentials.baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    private val api by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun getCoffeeApi(): ApiService {
        return api
    }

}