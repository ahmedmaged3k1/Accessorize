package com.example.araccessories.ui.features.productDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductDetailsViewModel: ViewModel() {
    var productName = MutableLiveData<String>("")
    var productDescription = MutableLiveData<String>("")
    var productPrice = MutableLiveData<String>("")


}