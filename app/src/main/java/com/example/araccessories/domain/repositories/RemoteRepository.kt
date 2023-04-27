package com.example.araccessories.domain.repositories

import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserLogin
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserRegister
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserResponse
import retrofit2.Response
import retrofit2.http.Header

interface RemoteRepository {
    suspend fun registerNewUser(user: UserRegister): Boolean
    suspend fun login(user: UserLogin): UserResponse?
    suspend fun getAllProducts(@Header("Authorization" )authToken : String): List<ProductsRemote>
}