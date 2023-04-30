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
import com.example.araccessories.domain.useCases.UserAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeFragmentViewModel@Inject constructor(private val productsUseCase: ProductsUseCase,private val userAccountUseCase: CacheProductsUseCase) :  ViewModel(){
     val productList = MutableLiveData<ArrayList<ProductsRemote>>()

    fun getAllProducts(authToken: String) {
        viewModelScope.launch {
            //productList.postValue(productsUseCase.getAllProducts(authToken) as ArrayList<ProductsRemote>?)
            userAccountUseCase.insertAllProducts(productsUseCase.getAllProducts(authToken))
        }

    }
     fun saveProducts(user: User,productList: List<ProductsRemote>){
        viewModelScope.launch {
                user.productsList=productList
                userAccountUseCase.overWriteUser(user)
        }
    }
    fun saveCartProduct(productsRemote: ProductsRemote){
        viewModelScope.launch {
            userAccountUseCase.insertProduct(productsRemote)
        }
    }
    fun deleteCartProduct(productsRemote: ProductsRemote){
        viewModelScope.launch {
            userAccountUseCase.deleteProduct(productsRemote)
        }
    }
     fun getAllCartProducts() {
     viewModelScope.launch {
         productList.postValue(userAccountUseCase.getAllProducts() as ArrayList<ProductsRemote>?)
     }
    }
    fun saveAllCartProducts(productList: List<ProductsRemote>){
        viewModelScope.launch {
           userAccountUseCase.insertAllProducts(productList)
        }
    }
    fun updateUserProducts(user: User, newProductsRemote: List<ProductsRemote>){
        viewModelScope.launch {
            userAccountUseCase.updateUser(user,newProductsRemote)
        }
    }

}