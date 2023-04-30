package com.example.araccessories.data.dataSource.localDataSource

import com.example.araccessories.data.dataSource.localDataSource.room.cacheDatabase.ProductsDao
import com.example.araccessories.data.dataSource.localDataSource.room.userDatabase.UserDao
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.domain.repositories.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepositoryImp @Inject constructor(private val productsDao: ProductsDao, private val userDao: UserDao) : LocalRepository {
    override suspend fun insertAll(products: List<ProductsRemote>) {
        withContext(Dispatchers.IO){
            productsDao.insertAll(products)
        }
    }

    override suspend fun getAllProducts(): List<ProductsRemote>? {
        return productsDao.getAllProducts()
    }

    override suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    override suspend fun updateUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.update(user)
        }
    }

    override suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.delete(user)
        }
    }

    override suspend fun getUserByEmail(name: String): List<User>? {
       return userDao.getUserByEmail(name)
    }

}