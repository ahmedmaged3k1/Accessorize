package com.example.araccessories.data.dataSource.remoteDataSource

import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.RegisterResponse
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserLogin
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserRegister
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    suspend fun registerNewUser(@Body params: UserRegister): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body params: UserLogin): Response<UserResponse?>
    @GET("products")
    suspend fun getAllProducts(@Header("Authorization" )authToken : String): Response<List<ProductsRemote>>


}