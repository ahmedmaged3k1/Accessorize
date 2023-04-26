package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("_id"         ) var Id          : String?  = null,
                @SerializedName("createdAt"   ) var createdAt   : String?  = null,
                @SerializedName("updatedAt"   ) var updatedAt   : String?  = null,
                @SerializedName("address"     ) var address     : Address? = Address(),
                @SerializedName("phoneNumber" ) var phoneNumber : String?  = null,
                @SerializedName("password"    ) var password    : String?  = null,
                @SerializedName("email"       ) var email       : String?  = null,
                @SerializedName("birthDate"   ) var birthDate   : String?  = null,
                @SerializedName("gender"      ) var gender      : String?  = null,
                @SerializedName("lastName"    ) var lastName    : String?  = null,
                @SerializedName("firstName"   ) var firstName   : String?  = null)
