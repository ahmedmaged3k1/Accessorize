package com.example.araccessories.ui.features.homeFragment

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.domain.useCases.CacheProductsUseCase
import com.example.araccessories.domain.useCases.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeFragmentViewModel@Inject constructor(private val productsUseCase: ProductsUseCase,private val userAccountUseCase: CacheProductsUseCase) :  ViewModel(){
     val productList = MutableLiveData<ArrayList<ProductsRemote>>()
    val filteredProductList = MutableLiveData<ArrayList<ProductsRemote>>()


    fun getAllProducts(authToken: String,userEmail : String) {
        viewModelScope.launch {
            productsUseCase.getAllProducts(authToken,userEmail)
            Log.d(TAG, "getAllProducts asd: $userEmail ")
            productList.postValue(  productsUseCase.getAllProducts(authToken,userEmail) as ArrayList<ProductsRemote>?)
        }

    }

    fun addToFavProduct(productsRemote: ProductsRemote){
        viewModelScope.launch{
            productsRemote.isFavourite=true
            userAccountUseCase.updateProduct(productsRemote)

        }
    }
    fun deleteFromFavProduct(productsRemote: ProductsRemote){
        viewModelScope.launch{
            productsRemote.isFavourite=false
            userAccountUseCase.updateProduct(productsRemote)

        }
    }


}