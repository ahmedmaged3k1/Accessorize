package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id"         ) var Id          : String?  = ".",
    @SerializedName("firstName"   ) var firstName   : String?  = ".",
    @SerializedName("lastName"    ) var lastName    : String?  = ".",
    @SerializedName("gender"      ) var gender      : String?  = ".",
    @SerializedName("birthDate"   ) var birthDate   : String?  = ".",
    @SerializedName("email"       ) var email       : String?  = ".",
    @SerializedName("password"    ) var password    : String?  = ".",
    @SerializedName("phoneNumber" ) var phoneNumber : String?  = ".",
    @SerializedName("address"     ) var address     : Address? = Address(),
    @SerializedName("createdAt"   ) var createdAt   : String?  = ".",
    @SerializedName("updatedAt"   ) var updatedAt   : String?  = ".",
    @SerializedName("__v"         ) var _v          : Int?     = 0
)
