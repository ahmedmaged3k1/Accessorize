package com.example.araccessories.ui.features.cartFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.databinding.ProductCartBinding


class CartFragmentRecyclerView (private val listener: CartFragmentRecyclerView.ProductCartClickListener):
    ListAdapter<ProductsRemote, CartFragmentRecyclerView.CartViewHolder>(diffCallbackCart) {
    interface ProductCartClickListener {
        fun onProductInc(position: Int)
        fun onProductDec(position: Int)
        fun orderPrice (price :  Int)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class CartViewHolder constructor(private val binding: ProductCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var orderTotal = 0

        fun bind(products: ProductsRemote) {
            var cartCount =1
            binding.product = products
            orderTotal+=products.price
            binding.increaseCount.setOnClickListener {
                cartCount++
                binding.productCartCount.text="$cartCount"
                orderTotal+=products.price
                listener.orderPrice(orderTotal)

                listener.onProductInc(position)
            }
            binding.decreaseCount.setOnClickListener {
                if (cartCount==1)return@setOnClickListener
                else{
                    orderTotal-=products.price
                   listener.onProductDec(position)
                    listener.orderPrice(orderTotal)

                    cartCount--
                    binding.productCartCount.text="$cartCount"
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

val diffCallbackCart = object : DiffUtil.ItemCallback<ProductsRemote>() {
    override fun areItemsTheSame(oldItem: ProductsRemote, newItem: ProductsRemote): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductsRemote, newItem: ProductsRemote): Boolean {
        return oldItem.Id == newItem.Id


    }


}