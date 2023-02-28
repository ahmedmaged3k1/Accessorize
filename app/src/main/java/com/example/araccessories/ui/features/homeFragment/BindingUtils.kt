package com.example.araccessories.ui.features.homeFragment

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Products

@BindingAdapter("productImage")
fun ImageView.setCoffeeImage(product: Products?) {
    product?.let {
        Glide.with(context)
            .load(product.productImage)
            .into(this)
    }
}



