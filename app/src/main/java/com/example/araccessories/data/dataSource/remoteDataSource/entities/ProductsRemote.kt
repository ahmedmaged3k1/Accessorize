package com.example.araccessories.data.dataSource.remoteDataSource.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.araccessories.data.dataSource.localDataSource.entities.Converters
import com.example.araccessories.data.dataSource.localDataSource.entities.Position
import com.example.araccessories.data.dataSource.localDataSource.entities.Scale
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity
@TypeConverters(Converters::class)
data class ProductsRemote(
    @PrimaryKey

    @SerializedName("_id"           ) var Id            : String,
    @SerializedName("name"          ) var name          : String,
    @SerializedName("brand"         ) var brand         : String,
    @SerializedName("price"         ) var price         : Int =0,
    @SerializedName("stock"         ) var stock         : Int,
    @SerializedName("sellerEmail"   ) var sellerEmail   : String,
    @SerializedName("category"      ) var category      : String,
    @SerializedName("origin"        ) var origin        : String,
    @SerializedName("color"         ) var color         : String,
    @SerializedName("images"        ) var images        : ArrayList<String> = arrayListOf(),
    @SerializedName("description"   ) var description   : String,
    @SerializedName("modelPosition" ) var modelPosition : @RawValue Position,
    @SerializedName("modelSize"     ) var modelSize     : @RawValue Scale,
    @SerializedName("rate"          ) var rate          : Double,
    @SerializedName("isTried"       ) var isTried       : Boolean,
    @SerializedName("isFavourite"   ) var isFavourite   : Boolean,
    @SerializedName("isCart"        ) var isCart        : Boolean,
    @SerializedName("__v"           ) var _v            : Int,
    @SerializedName("modelLink"     ) var modelLink     : String
): Parcelable
