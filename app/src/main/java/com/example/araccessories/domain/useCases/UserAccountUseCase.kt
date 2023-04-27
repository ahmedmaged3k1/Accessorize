package com.example.araccessories.domain.useCases

import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserLogin
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserRegister
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserResponse
import com.example.araccessories.domain.repositories.RemoteRepository

class UserAccountUseCase (private val remoteRepositoryImp: RemoteRepository) {
    suspend fun loginUser(user: UserLogin): UserResponse? {
        return remoteRepositoryImp.login(user)
    }
    suspend fun registerNewUser(user: UserRegister): Boolean {
        return remoteRepositoryImp.registerNewUser(user)
    }


}