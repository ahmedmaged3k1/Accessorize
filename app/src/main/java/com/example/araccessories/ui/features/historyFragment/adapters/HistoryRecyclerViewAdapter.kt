package com.example.araccessories.ui.features.historyFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.databinding.ProductHistoryBinding
import com.example.araccessories.ui.features.homeFragment.adapters.ProductsRecyclerViewAdapter


class HistoryRecyclerViewAdapter (private val listener: ProductRemoveClickListener) :
    ListAdapter<ProductsRemote, HistoryRecyclerViewAdapter.HistoryViewHolder>(diffCallbackHistory) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return from(parent)
    }
    interface ProductRemoveClickListener {
        fun onProductRemove(products: ProductsRemote)

    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class HistoryViewHolder constructor(private val binding: ProductHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(products: ProductsRemote) {
            binding.product = products
            binding.removeProduct.setOnClickListener {
                listener.onProductRemove(products)
            }


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

val diffCallbackHistory = object : DiffUtil.ItemCallback<ProductsRemote>() {
    override fun areItemsTheSame(oldItem: ProductsRemote, newItem: ProductsRemote): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProductsRemote, newItem: ProductsRemote): Boolean {
        return oldItem.Id == newItem.Id


    }


}