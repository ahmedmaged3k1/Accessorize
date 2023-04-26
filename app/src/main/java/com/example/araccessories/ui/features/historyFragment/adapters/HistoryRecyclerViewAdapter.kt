package com.example.araccessories.ui.features.historyFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.databinding.ProductHistoryBinding


class HistoryRecyclerViewAdapter :
    ListAdapter<Products, HistoryRecyclerViewAdapter.HistoryViewHolder>(diffCallbackHistory) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class HistoryViewHolder constructor(private val binding: ProductHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(products: Products) {
            binding.product = products
            binding.productName.text=products.productName
            binding.productPrice.text = "${products.productPrice} Egp"
            binding.productImage.setImageResource(products.productImage[0])

            binding.executePendingBindings()
        }

        init {

        }

    }

    fun from(parent: ViewGroup): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductHistoryBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding)
    }
}

val diffCallbackHistory = object : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.productId == newItem.productId


    }


}