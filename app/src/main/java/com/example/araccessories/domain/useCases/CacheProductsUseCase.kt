package com.example.araccessories.domain.useCases

import com.example.araccessories.data.dataSource.localDataSource.LocalRepositoryImp
import com.example.araccessories.domain.repositories.LocalRepository
import javax.inject.Inject

class CacheProductsUseCase@Inject constructor(private val localRepository: LocalRepository) {

}