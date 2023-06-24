package com.example.araccessories.ui.features.productDetails

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.domain.useCases.CacheProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Objects
import javax.inject.Inject
@HiltViewModel
class ProductDetailsViewModel @Inject constructor( private val userAccountUseCase: CacheProductsUseCase): ViewModel() {
    var productName = MutableLiveData<String>("")
    var productDescription = MutableLiveData<String>("")
    var productPrice = MutableLiveData<String>("")
    val productList = MutableLiveData<ArrayList<ProductsRemote>>()
    var bool: Boolean? = false

    fun checkIfAddedToCart(userEmail: String, productsRemote: ProductsRemote): Boolean? {
        viewModelScope.launch {
            val cartProducts = userAccountUseCase.getProductsByEmail(userEmail)?.filter { it.isCart }

            cartProducts?.forEach {
                if (it.name.trim().equals(productsRemote.name.trim(), ignoreCase = true)) {
                    bool = true
                    return@launch
                }
                else{
                    bool=false
                }
            }
        }

        return bool
    }


    fun addToHistoryProduct(productsRemote: ProductsRemote){
        viewModelScope.launch{
            productsRemote.isTried=true
            userAccountUseCase.updateProduct(productsRemote)

        }
    }
    fun addToCartProduct(productsRemote: ProductsRemote){
        viewModelScope.launch{
            productsRemote.isCart=true
            userAccountUseCase.updateProduct(productsRemote)

        }
    }
    fun shareImage (context: Context, imageUrL: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val bmp = BitmapFactory.decodeStream(URL(imageUrL).openStream())
                val outputStream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
                val tempFile = File.createTempFile("temp", ".png")
                val fileOutputStream = FileOutputStream(tempFile)
                fileOutputStream.write(outputStream.toByteArray())
                fileOutputStream.close()
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", tempFile)
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                shareIntent.type = "image/png"
                shareIntent.setPackage("com.whatsapp")
                context.startActivity(Intent.createChooser(shareIntent, "Share with"))


            }

        }

    }


}