package com.example.araccessories.ui.features.homeFragment

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
            productList.postValue(productsUseCase.getAllProducts(authToken) as ArrayList<ProductsRemote>?)
        }

    }
     fun saveProducts(user: User,productList: List<ProductsRemote>){
        viewModelScope.launch {
                user.productsList=productList
                userAccountUseCase.overWriteUser(user)



        }
    }

}