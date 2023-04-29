package com.example.araccessories.domain.repositories

import com.example.araccessories.data.dataSource.localDataSource.room.cacheDatabase.ProductsDao

interface LocalRepository {
    suspend fun insert(product: ProductsDao)


    suspend fun update(product: ProductsDao)


    suspend fun delete(product: ProductsDao)


    suspend fun getProduct(name: String): List<ProductsDao>?


    suspend fun insertAllCacheProducts(product: List<ProductsDao>)

    suspend fun getCacheProduct(): List<ProductsDao>?
}