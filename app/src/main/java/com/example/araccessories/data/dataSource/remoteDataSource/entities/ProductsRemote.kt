package com.example.araccessories.data.dataSource.remoteDataSource.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.araccessories.data.dataSource.localDataSource.entities.Converters
import com.google.gson.annotations.SerializedName
@Entity
@TypeConverters(Converters::class)
data class ProductsRemote(
    @PrimaryKey
    @NonNull
    @SerializedName("_id"         ) var Id          : String         ,
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
