package com.example.araccessories.domain.repositories

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.araccessories.data.dataSource.localDataSource.room.cacheDatabase.ProductsDao
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User

interface LocalRepository {


    suspend fun insertAll(products: List<ProductsRemote>)

    suspend fun getAllProducts(): List<ProductsRemote>?
    suspend fun insertUser(user: User)


    suspend fun updateUser(user: User)


    suspend fun deleteUser(user: User)


    suspend fun getUserByEmail(name: String): User?

    suspend fun insertAll(user: User)

}