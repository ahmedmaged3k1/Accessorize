package com.example.araccessories.domain.useCases

import android.content.ContentValues.TAG
import android.util.Log
import com.example.araccessories.data.dataSource.localDataSource.LocalRepositoryImp
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.repositories.LocalRepository
import com.example.araccessories.domain.repositories.RemoteRepository

class ProductsUseCase(private val remoteRepositoryImp: RemoteRepository,private val localRepository: LocalRepository) {
    suspend fun getAllProducts(authToken : String,userEmail: String) : List<ProductsRemote>?{
       // localRepository.insertAll(remoteRepositoryImp.getAllProducts(authToken))
        val userEmailList = remoteRepositoryImp.getAllProducts(authToken).onEach { it.userEmail = userEmail}
        if (localRepository.getProductsByEmail(userEmail)?.size==userEmailList.size)
        {
            return localRepository.getProductsByEmail(userEmail)

        }
        if  (userEmailList.isEmpty()) return emptyList()
        else
        {
            userEmailList.onEach { it.Id="${it.Id} : ${it.userEmail}" }
            localRepository.insertAllProducts(userEmailList)
            return localRepository.getProductsByEmail(userEmail)
        }

    }
    suspend fun getAllProductsByEmail(authToken : String,userEmail :String) : List<ProductsRemote>?{
        // localRepository.insertAll(remoteRepositoryImp.getAllProducts(authToken))

        return localRepository.getProductsByEmail(userEmail)
    }
}