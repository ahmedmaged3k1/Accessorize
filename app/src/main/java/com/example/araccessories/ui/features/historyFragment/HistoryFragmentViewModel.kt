package com.example.araccessories.ui.features.historyFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.useCases.CacheProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryFragmentViewModel @Inject constructor(private val userAccountUseCase: CacheProductsUseCase) :  ViewModel(){
    val productList = MutableLiveData<ArrayList<ProductsRemote>>()
    fun getAllProducts(userEmail: String) {
        viewModelScope.launch {
            productList.postValue(
                userAccountUseCase.getProductsByEmail(userEmail)
                    ?.filter { it.isTried } as ArrayList<ProductsRemote>?)
        }
    }

    }