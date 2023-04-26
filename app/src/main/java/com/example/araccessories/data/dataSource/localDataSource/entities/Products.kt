package com.example.araccessories.data.dataSource.localDataSource.entities

import android.os.Parcelable
import com.google.ar.sceneform.rendering.ModelRenderable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize

data class Products(
    var productId: String,
    var productName: String,
    var productImage: List<Int>,
    var productPrice: Double,
    var productRate: Double,
    var categoryId:Int,
    var productFav:Int,
    var productModel: @RawValue ModelRenderable? =null,
    var productDescription: String,
    var localScale: @RawValue Scale?,
    var localPosition:@RawValue Position?,
    var productCategory: String = "",
    var triedProduct : Boolean = false,
    var productCount : Int =1
    ):Parcelable
