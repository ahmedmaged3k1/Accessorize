package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data class UserLogin(
    @SerializedName("email"       ) var email       : String?  = "",
    @SerializedName("password"    ) var password    : String?  = ""
                     )
