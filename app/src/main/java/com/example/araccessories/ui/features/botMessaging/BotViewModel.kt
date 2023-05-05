package com.example.araccessories.ui.features.botMessaging

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.useCases.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BotViewModel @Inject constructor(private val productsUseCase: ProductsUseCase): ViewModel() {
     lateinit var productRemote : ProductsRemote
    var observer = MutableLiveData(1)

    fun searchByName(name : String){
        viewModelScope.launch {

            productRemote =productsUseCase.searchByName(name)!!.get(0)
            manipulateLiveData(observer)

        }
    }

    private fun manipulateLiveData(liveData: MutableLiveData<Int>) {
        liveData.value = liveData.value?.inc()

    }
}