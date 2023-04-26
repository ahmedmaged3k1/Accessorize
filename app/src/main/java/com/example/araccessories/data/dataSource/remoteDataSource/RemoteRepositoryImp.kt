package com.example.araccessories.data.dataSource.remoteDataSource

import android.content.ContentValues.TAG
import android.util.Log
import com.example.araccessories.data.dataSource.remoteDataSource.entities.User
import com.example.araccessories.domain.repositories.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImp @Inject constructor(private val apiService: ApiService) : RemoteRepository {
    override suspend fun registerNewUser(user: User): Boolean {
        try {
            val response = apiService.registerNewUser(user).code() ?: false
            if (response == 200)
                return true

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override suspend fun login(user: User): User? {
        try {
            val responseCode = apiService.login(user).code()
            Log.d(TAG, "login response code: $responseCode")
            val response = apiService.login(user).body()
            Log.d(TAG, "login response: $response")

            if (responseCode == 200)
                return response
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "login: ${e.message}")


        }
        return null
    }


}