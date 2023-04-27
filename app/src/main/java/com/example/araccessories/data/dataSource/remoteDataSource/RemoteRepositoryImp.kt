package com.example.araccessories.data.dataSource.remoteDataSource

import android.content.ContentValues.TAG
import android.util.Log
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserLogin
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserRegister
import com.example.araccessories.data.dataSource.remoteDataSource.entities.UserResponse
import com.example.araccessories.domain.repositories.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImp @Inject constructor(private val apiService: ApiService) : RemoteRepository {
    private lateinit var productsList: List<ProductsRemote>

    override suspend fun registerNewUser(user: UserRegister): Boolean {
        try {
            val response = apiService.registerNewUser(user).code()

            if (response == 200)
                return true

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "registerNewUser:  response error ${e.message}")

        }
        return false
    }

    override suspend fun login(user: UserLogin): UserResponse? {
        try {
            val responseCode = apiService.login(user).code()
            val response = apiService.login(user).body()
            if (responseCode == 200)
                return response
        } catch (e: Exception) {
            e.printStackTrace()


        }
        return null
    }

    override suspend fun getAllProducts(authToken: String) : List<ProductsRemote> {
        withContext(Dispatchers.Default) {
            try {
                val response = apiService.getAllProducts(authToken).body() ?: listOf()
                productsList = response
                Log.d(TAG, "getAllProducts: success ${productsList.toString()}")

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "getAllProducts:  error ")
            }
        }
        return productsList
    }


}