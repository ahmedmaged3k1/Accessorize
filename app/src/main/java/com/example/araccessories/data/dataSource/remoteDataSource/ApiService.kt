package com.example.araccessories.data.dataSource.remoteDataSource

import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    suspend fun registerNewUser(@Body params: User): Response<UserResponse>

    @POST("login")
    suspend fun login(@Body params: User): Response<User>

}