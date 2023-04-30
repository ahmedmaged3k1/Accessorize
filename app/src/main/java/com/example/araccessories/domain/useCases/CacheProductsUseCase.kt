package com.example.araccessories.domain.useCases

import com.example.araccessories.data.dataSource.localDataSource.LocalRepositoryImp
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.domain.repositories.LocalRepository
import javax.inject.Inject

class CacheProductsUseCase@Inject constructor(private val localRepository: LocalRepository) {

    suspend fun insertUser(user: User){
        localRepository.insertUser(user)
    }
    suspend fun overWriteUser(user: User){
        localRepository.insertAll(user)
    }


    suspend fun updateUser(user: User){
        localRepository.updateUser(user)

    }


    suspend fun deleteUser(user: User){
        localRepository.deleteUser(user)

    }


    suspend fun getUserByEmail(name: String): User?{
        return localRepository.getUserByEmail(name)
    }
}