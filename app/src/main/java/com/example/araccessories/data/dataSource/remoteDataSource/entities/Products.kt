package com.example.araccessories.data.dataSource.remoteDataSource.entities

import android.os.Parcelable
import com.google.ar.sceneform.rendering.ModelRenderable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Products(
    var productId: Int,
    var productName: String,
    var productImage: List<Int>,
    var productPrice: Double,
    var productRate: Double,
    var categoryId:Int,
    var productFav:Int,

    var productModel: @RawValue ModelRenderable? =null,
    var productDescription: String,
    ):Parcelable
