package com.example.araccessories.ui.features.homeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.useCases.ProductsUseCase
import com.example.araccessories.domain.useCases.UserAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeFragmentViewModel@Inject constructor(private val productsUseCase: ProductsUseCase) :  ViewModel(){
     val productList = MutableLiveData<ArrayList<ProductsRemote>>()
    fun getAllProducts(authToken: String) {
        viewModelScope.launch {
            productList.postValue(productsUseCase.getAllProducts(authToken) as ArrayList<ProductsRemote>?)
        }

    }

}