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


    suspend fun updateUser(user: User, newProductsRemote: List<ProductsRemote>)


    suspend fun deleteUser(user: User)

    suspend fun insertProduct(productsRemote: ProductsRemote)


    suspend fun updateProduct(productsRemote: ProductsRemote)


    suspend fun deleteProduct(productsRemote: ProductsRemote)

    suspend fun getProductsByEmail(name: String): List<ProductsRemote>?

    suspend fun getUserByEmail(name: String): User?

    suspend fun insertAllUsers(user: User)

}