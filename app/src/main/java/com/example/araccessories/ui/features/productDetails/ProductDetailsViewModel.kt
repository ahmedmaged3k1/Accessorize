package com.example.araccessories.ui.features.productDetails

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
class ProductDetailsViewModel @Inject constructor( private val userAccountUseCase: CacheProductsUseCase): ViewModel() {
    var productName = MutableLiveData<String>("")
    var productDescription = MutableLiveData<String>("")
    var productPrice = MutableLiveData<String>("")

    fun addToFavProduct(productsRemote: ProductsRemote){
        viewModelScope.launch{
            productsRemote.isTried=true
            userAccountUseCase.updateProduct(productsRemote)

        }
    }
}