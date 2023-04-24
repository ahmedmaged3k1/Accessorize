package com.example.araccessories.ui.features.cartFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Ad
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Products
import com.example.araccessories.databinding.AdItemBinding
import com.example.araccessories.databinding.ProductCartBinding
import com.example.araccessories.databinding.ProductItemBinding


class CartFragmentRecyclerView :
    ListAdapter<Products, CartFragmentRecyclerView.CartViewHolder>(diffCallbackCart) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class CartViewHolder constructor(private val binding: ProductCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(products: Products) {
            var cartCount =1
            binding.product = products
            binding.productName.text=products.productName
            binding.productPrice.text = "${products.productPrice} Egp"
            binding.productImage.setImageResource(products.productImage[0])
            binding.productCartCount.text="$cartCount"
            binding.increaseCount.setOnClickListener {
                cartCount++
                binding.productCartCount.text="${cartCount  }"

            }
            binding.decreaseCount.setOnClickListener {
                if (cartCount==1)return@setOnClickListener
                else{
                    cartCount--
                    binding.productCartCount.text="${cartCount}"
                }
            }



            binding.executePendingBindings()
        }

        init {

        }

    }

    fun from(parent: ViewGroup): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }
}

val diffCallbackCart = object : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.productId == newItem.productId


    }


}