package com.example.araccessories.domain.useCases

import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.domain.repositories.RemoteRepository

class UserLoginUseCase (private val remoteRepositoryImp: RemoteRepository) {
    suspend fun loginUser(user: User): User? {
        return remoteRepositoryImp.login(user)
    }


}