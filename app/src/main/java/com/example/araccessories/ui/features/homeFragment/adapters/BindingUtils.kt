package com.example.araccessories.ui.features.homeFragment.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.araccessories.data.dataSource.localDataSource.entities.Products

@BindingAdapter("productImage")
fun ImageView.setProductImage(product: Products?) {
    product?.let {
        Glide.with(context)
            .load(product.productImage)
            .into(this)
    }
}
/*@BindingAdapter("adImages")
fun ImageView.setAdImage( imageView :ImageView,  resource : Int) {
    imageView.setImageResource(resource)

}*/



