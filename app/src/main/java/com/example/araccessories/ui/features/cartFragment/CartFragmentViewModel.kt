package com.example.araccessories.ui.features.cartFragment

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.useCases.CacheProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartFragmentViewModel @Inject constructor(private val userAccountUseCase: CacheProductsUseCase) : ViewModel() {
    val productList = MutableLiveData<ArrayList<ProductsRemote>>()
    var orderTotal = MutableLiveData(0)
    var orderPrice =0
    fun getAllProducts(userEmail: String) {
        viewModelScope.launch {
            productList.postValue(userAccountUseCase.getProductsByEmail(userEmail)?.filter { it.isCart } as ArrayList<ProductsRemote>?)


            var cartProducts =userAccountUseCase.getProductsByEmail(userEmail)?.filter { it.isCart }
            cartProducts!!.forEach {
                    orderPrice+=it.price
                }
            orderTotal.postValue(orderPrice)

        }
    }

    fun increasePrice(position :Int){
       orderTotal.value = orderTotal.value?.plus(productList.value?.get(position)!!.price*2)
    }
    fun decreasePrice(position :Int){
        orderTotal.value = orderTotal.value?.minus(productList.value?.get(position)!!.price/2)

    }
    fun changeTotal(price : Int){
        orderTotal.postValue(price)
        Log.d(TAG, "changeTotal:  price $price ")

    }
}