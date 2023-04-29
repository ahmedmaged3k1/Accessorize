package com.example.araccessories.ui.features.homeFragment.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote

@BindingAdapter("productImage")
fun ImageView.setProductImage(product: ProductsRemote?) {

    product?.let {
        Glide.with(context)
            .load(product.images[0])
            .into(this)
    }
}
@BindingAdapter("productImages")
fun ImageView.setProductImages(image: String?) {

    image.let {
        Glide.with(context)
            .load(image)
            .into(this)
    }
}
/*@BindingAdapter("adImages")
fun ImageView.setAdImage( imageView :ImageView,  resource : Int) {
    imageView.setImageResource(resource)

}*/



