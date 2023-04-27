package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("country"  ) var country  : String? = ".",
    @SerializedName("state"    ) var state    : String? = ".",
    @SerializedName("city"     ) var city     : String? = ".",
    @SerializedName("street"   ) var street   : String? = ".",
    @SerializedName("floorNum" ) var floorNum : Int?    = 0,
    @SerializedName("aptNum"   ) var aptNum   : Int?    = 0,
    @SerializedName("zipCode"  ) var zipCode  : Int?    = 0)
