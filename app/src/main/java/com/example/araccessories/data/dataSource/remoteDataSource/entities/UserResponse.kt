package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data  class UserResponse (
    @SerializedName("token" ) var token : String? = null,
    @SerializedName("user"  ) var user  : User?   = User(email = ".")

)
