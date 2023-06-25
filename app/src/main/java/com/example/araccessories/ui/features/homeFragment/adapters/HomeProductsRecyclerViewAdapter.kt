package com.example.araccessories.ui.features.homeFragment.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.databinding.ProductItemBinding
import com.example.araccessories.ui.features.mainNavigation.MainNavigationDirections
import kotlinx.android.synthetic.main.product_item.view.product_item


class ProductsRecyclerViewAdapter(private val listener: ProductFavClickListener) :
    ListAdapter<ProductsRemote, ProductsRecyclerViewAdapter.ProductViewHolder>(diffCallback) {
    interface ProductFavClickListener {
        fun onProductAdd(position: Int)
        fun onProductRemove(position: Int)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
     //   if (getItem(position).images.size==0)return
      holder.bind(getItem(position))
    }
    inner class ProductViewHolder constructor(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind (product : ProductsRemote){
                binding.product=product
                Log.d(TAG, "onProductFav:  ${product.isFavourite}")
                if (product.isFavourite){
                    binding.productFav.setImageResource(R.drawable.ripple_heart)
                }
                else {
                    binding.productFav.setImageResource(R.drawable.ripple_no_heart)

                }
                binding.productFav.setOnClickListener {
                    if (product.isFavourite){
                        binding.productFav.setImageResource(R.drawable.ripple_no_heart)
                        product.isFavourite=false
                        listener.onProductRemove(position)
                    }
                    else {
                        binding.productFav.setImageResource(R.drawable.ripple_heart)
                        listener.onProductAdd(position)
                        product.isFavourite=true

                    }
                }
                binding.root.product_item.setOnClickListener {
                    //val extras = FragmentNavigatorExtras(binding to "transitionImage")
                    val extras = FragmentNavigatorExtras(
                        binding.productImage to "imageView"
                    )
                    val action =
                        MainNavigationDirections.actionMainNavigationToProductDetailsFragment(
                            getItem(position),position
                        )
                    binding.root.findNavController()
                        .navigate(action,extras)

                }
                binding.executePendingBindings()
            }

        init {

        }



    }
    fun from(parent: ViewGroup): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }
}
val diffCallback = object : DiffUtil.ItemCallback<ProductsRemote>() {
    override fun areItemsTheSame(oldItem: ProductsRemote, newItem: ProductsRemote): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductsRemote, newItem: ProductsRemote): Boolean {
        return oldItem.Id == newItem.Id


    }


}