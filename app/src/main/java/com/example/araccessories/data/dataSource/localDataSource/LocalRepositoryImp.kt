package com.example.araccessories.data.dataSource.localDataSource

import android.content.ContentValues.TAG
import android.util.Log
import com.example.araccessories.data.dataSource.localDataSource.room.cacheDatabase.ProductsDao
import com.example.araccessories.data.dataSource.localDataSource.room.userDatabase.UserDao
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.domain.repositories.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepositoryImp @Inject constructor(private val productsDao: ProductsDao, private val userDao: UserDao) : LocalRepository {

    override suspend fun insertProduct(productsRemote: ProductsRemote) {
        withContext(Dispatchers.IO){
            productsDao.insertProduct(productsRemote)
        }
    }
    override suspend fun insertAllProducts(products: List<ProductsRemote>) {
        withContext(Dispatchers.IO){

                productsDao.insertAllProducts(products)

        }
    }

    override suspend fun insertAllUsers(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertAll(user)
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

    override suspend fun updateUser(user: User, newProductsRemote: List<ProductsRemote>) {
        withContext(Dispatchers.IO) {
            user.productsList=newProductsRemote
            userDao.update(user)
        }
    }

    override suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.delete(user)
        }
    }



    override suspend fun updateProduct(productsRemote: ProductsRemote) {
        withContext(Dispatchers.IO){
            productsDao.update(productsRemote)
        }
    }

    override suspend fun getProductsByName(name: String): List<ProductsRemote>? {
        return productsDao.getProductsByName(name)
    }

    override suspend fun deleteProduct(productsRemote: ProductsRemote) {
        withContext(Dispatchers.IO){
            productsDao.delete(productsRemote)
        }
    }

    override suspend fun getProductsByEmail(name: String): List<ProductsRemote>? {
        return productsDao.getProductsByEmail(name)
    }

    override suspend fun getUserByEmail(name: String): User? {
       return userDao.getUserByEmail(name)
    }

}