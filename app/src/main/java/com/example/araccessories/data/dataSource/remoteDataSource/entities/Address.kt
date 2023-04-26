package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("country"  ) var country  : String? = null,
    @SerializedName("state"    ) var state    : String? = null,
    @SerializedName("city"     ) var city     : String? = null,
    @SerializedName("street"   ) var street   : String? = null,
    @SerializedName("floorNum" ) var floorNum : Int?    = null,
    @SerializedName("aptNum"   ) var aptNum   : Int?    = null,
    @SerializedName("zipCode"  ) var zipCode  : Int?    = null)
