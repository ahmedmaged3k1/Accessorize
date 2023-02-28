package com.example.araccessories.data.dataSource.remoteDataSource.entities

data class Products(
    var productId: Int,
    var productName: String,
    var productImage: String,
    var productPrice: Double,
    var productRate: Double,
    var categoryId:Int,
    var sellerEmail:String
    )
