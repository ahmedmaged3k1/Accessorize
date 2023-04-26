package com.example.araccessories.domain.repositories

import com.example.araccessories.data.dataSource.remoteDataSource.entities.User

interface RemoteRepository {
    suspend fun registerNewUser(user: User): Boolean
    suspend fun login(user: User): User?
}