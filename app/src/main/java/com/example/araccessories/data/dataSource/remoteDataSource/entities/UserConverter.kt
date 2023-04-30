package com.example.araccessories.data.dataSource.remoteDataSource.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserConverter {
    @TypeConverter
    fun fromProductsRemoteList(productsRemoteList: List<ProductsRemote>?): String? {
        return Gson().toJson(productsRemoteList)
    }
    @TypeConverter
    fun toProductsRemoteList(productsRemoteJson: String?): List<ProductsRemote>? {
        val productsRemoteType = object : TypeToken<List<ProductsRemote>?>() {}.type
        return Gson().fromJson(productsRemoteJson, productsRemoteType)
    }
    @TypeConverter
    fun fromAddress(address: Address?): String? {
        return Gson().toJson(address)
    }

    @TypeConverter
    fun toAddress(addressJson: String?): Address? {
        val addressType = object : TypeToken<Address?>() {}.type
        return Gson().fromJson(addressJson, addressType)
    }



}