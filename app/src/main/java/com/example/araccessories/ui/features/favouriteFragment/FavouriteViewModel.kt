package com.example.araccessories.ui.features.favouriteFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.useCases.CacheProductsUseCase
import com.example.araccessories.domain.useCases.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel@Inject constructor( private val userAccountUseCase: CacheProductsUseCase) :  ViewModel(){
    val productList = MutableLiveData<ArrayList<ProductsRemote>>()
    fun getAllProducts() {
        viewModelScope.launch {
            productList.postValue(userAccountUseCase.getAllProducts()?.filter { it.isFavourite } as ArrayList<ProductsRemote>?)
        }

    }

}