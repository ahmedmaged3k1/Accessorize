package com.example.araccessories.domain.repositories

import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserLogin
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserRegister
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserResponse

interface RemoteRepository {
    suspend fun registerNewUser(user: UserRegister): Boolean
    suspend fun login(user: UserLogin): UserResponse?
}