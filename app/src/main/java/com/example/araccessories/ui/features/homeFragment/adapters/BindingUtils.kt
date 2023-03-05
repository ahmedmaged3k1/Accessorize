package com.example.araccessories.ui.features.homeFragment

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Ad
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Products

@BindingAdapter("productImage")
fun ImageView.setProductImage(product: Products?) {
    product?.let {
        Glide.with(context)
            .load(product.productImage)
            .into(this)
    }
}
@BindingAdapter("adImage")
fun ImageView.setAdImage(ad: Ad?) {
    ad?.let {
        Glide.with(context)
            .load(ad.adImage)
            .into(this)
    }
}



