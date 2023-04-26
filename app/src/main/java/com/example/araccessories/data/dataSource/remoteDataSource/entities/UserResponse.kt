package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data  class UserResponse (
    @SerializedName("message" ) var message : String? = null)