package com.example.araccessories.data.dataSource.remoteDataSource.entities

import com.google.ar.sceneform.rendering.ModelRenderable

data class Products(
    var productId: Int,
    var productName: String,
    var productImage: Int,
    var productPrice: Double,
    var productRate: Double,
    var categoryId:Int,
    var productFav:Int,
    var productModel: ModelRenderable? =null
    )
