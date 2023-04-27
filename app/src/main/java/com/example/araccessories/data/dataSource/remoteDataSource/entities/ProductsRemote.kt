package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.gson.annotations.SerializedName

data class ProductsRemote(
    @SerializedName("_id"         ) var Id          : String?           = null,
    @SerializedName("name"        ) var name        : String?           = null,
    @SerializedName("price"       ) var price       : Int?              = null,
    @SerializedName("stock"       ) var stock       : Int?              = null,
    @SerializedName("sellerEmail" ) var sellerEmail : String?           = null,
    @SerializedName("category"    ) var category    : String?           = null,
    @SerializedName("origin"      ) var origin      : String?           = null,
    @SerializedName("color"       ) var color       : String?           = null,
    @SerializedName("model"       ) var model       : String?           = null,
    @SerializedName("images"      ) var images      : ArrayList<String> = arrayListOf(),
    @SerializedName("description" ) var description : String?           = null,
    @SerializedName("createdAt"   ) var createdAt   : String?           = null,
    @SerializedName("updatedAt"   ) var updatedAt   : String?           = null,
    @SerializedName("__v"         ) var _v          : Int?              = null,
    @SerializedName("brand"       ) var brand       : String?           = null
)
