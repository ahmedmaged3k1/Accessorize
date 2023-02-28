package com.example.araccessories.ui.features.homeFragment

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Products
import com.example.araccessories.databinding.FragmentHomeBinding
import com.example.araccessories.databinding.ProductItemBinding
import android.view.LayoutInflater
import androidx.navigation.findNavController


class ProductsRecyclerViewAdapter :
    ListAdapter<Products, ProductsRecyclerViewAdapter.ProductViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
      holder.bind(getItem(position))
    }
    inner class ProductViewHolder constructor(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind (product :Products){
                binding.product=product
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
val diffCallback = object : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.productId == newItem.productId


    }


}