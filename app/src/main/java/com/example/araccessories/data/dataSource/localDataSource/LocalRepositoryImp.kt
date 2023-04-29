package com.example.araccessories.data.dataSource.localDataSource

import com.example.araccessories.data.dataSource.localDataSource.room.cacheDatabase.ProductsDao
import com.example.araccessories.domain.repositories.LocalRepository

class LocalRepositoryImp : LocalRepository {
    override suspend fun insert(product: ProductsDao) {
        TODO("Not yet implemented")
    }

    override suspend fun update(product: ProductsDao) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(product: ProductsDao) {
        TODO("Not yet implemented")
    }

    override suspend fun getProduct(name: String): List<ProductsDao>? {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllCacheProducts(product: List<ProductsDao>) {
        TODO("Not yet implemented")
    }

    override suspend fun getCacheProduct(): List<ProductsDao>? {
        TODO("Not yet implemented")
    }
}