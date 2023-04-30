package com.example.araccessories.data.dataSource.remoteDataSource.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity
@TypeConverters(UserConverter::class)
data class User(
    @SerializedName("_id"         ) var Id          : String?  = ".",
    @SerializedName("firstName"   ) var firstName   : String?  = ".",
    @SerializedName("lastName"    ) var lastName    : String?  = ".",
    @SerializedName("gender"      ) var gender      : String?  = ".",
    @SerializedName("birthDate"   ) var birthDate   : String?  = ".",
    @PrimaryKey
    @NonNull
    @SerializedName("email"       ) var email       : String,
    @SerializedName("password"    ) var password    : String?  = ".",
    @SerializedName("phoneNumber" ) var phoneNumber : String?  = ".",
    @SerializedName("address"     ) var address     : Address? = Address(),
    @SerializedName("createdAt"   ) var createdAt   : String?  = ".",
    @SerializedName("updatedAt"   ) var updatedAt   : String?  = ".",
    @SerializedName("__v"         ) var _v          : Int?     = 0,
    var productsList : List<ProductsRemote>?=null
)
