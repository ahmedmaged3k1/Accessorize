package com.example.araccessories.domain.useCases

import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.repositories.RemoteRepository

class ProductsUseCase(private val remoteRepositoryImp: RemoteRepository) {
    suspend fun getAllProducts(authToken : String) : List<ProductsRemote>{
        return remoteRepositoryImp.getAllProducts(authToken)
    }
}