package com.example.araccessories.domain.useCases

import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.domain.repositories.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CacheProductsUseCase@Inject constructor(private val localRepository: LocalRepository) {

    suspend fun insertUser(user: User){
        localRepository.insertUser(user)
    }
    suspend fun insertProduct(productsRemote: ProductsRemote){
        localRepository.insertProduct(productsRemote)
    }
    suspend fun updateProduct(productsRemote: ProductsRemote){
        localRepository.updateProduct(productsRemote)
    }
    suspend fun deleteProduct(productsRemote: ProductsRemote){
        localRepository.deleteProduct(productsRemote)
    }
    suspend fun getAllProducts() : List<ProductsRemote>?  {

        return localRepository.getAllProducts()
    }
    suspend fun overWriteUser(user: User){
        localRepository.insertAllUsers(user)
    }


    suspend fun updateUser(user: User, newProductsRemote: List<ProductsRemote>){
        localRepository.updateUser(user,newProductsRemote)

    }


    suspend fun deleteUser(user: User){
        localRepository.deleteUser(user)

    }


    suspend fun getUserByEmail(name: String): User?{
        return localRepository.getUserByEmail(name)
    }
    suspend fun insertAllProducts(products: List<ProductsRemote>) {
        withContext(Dispatchers.IO){

            localRepository.insertAllProducts(products)
        }
    }
}