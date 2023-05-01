package com.example.araccessories.domain.useCases

import com.example.araccessories.data.dataSource.localDataSource.LocalRepositoryImp
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.repositories.LocalRepository
import com.example.araccessories.domain.repositories.RemoteRepository

class ProductsUseCase(private val remoteRepositoryImp: RemoteRepository,private val localRepository: LocalRepository) {
    suspend fun getAllProducts(authToken : String) : List<ProductsRemote>?{
       // localRepository.insertAll(remoteRepositoryImp.getAllProducts(authToken))
        localRepository.insertAllProducts(remoteRepositoryImp.getAllProducts(authToken))
        return localRepository.getAllProducts()
    }
}