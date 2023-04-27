package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data class UserRegister(
                        @SerializedName("firstName"   ) var firstName   : String?  = ".",
                        @SerializedName("lastName"    ) var lastName    : String?  = ".",
                        @SerializedName("gender"      ) var gender      : String?  = ".",
                        @SerializedName("birthDate"   ) var birthDate   : String?  = ".",
                        @SerializedName("email"       ) var email       : String?  = ".",
                        @SerializedName("password"    ) var password    : String?  = ".",
                        @SerializedName("phoneNumber" ) var phoneNumber : String?  = ".",
                        @SerializedName("address"     ) var address     : Address? = Address(),

)
