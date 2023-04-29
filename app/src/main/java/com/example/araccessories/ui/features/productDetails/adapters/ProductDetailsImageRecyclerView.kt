package com.example.araccessories.ui.features.productDetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.databinding.ProductDetailsImageItemBinding


val diffCallbackInt= object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem


    }



}
class ProductImageRecyclerViewAdapter :
    ListAdapter<String, ProductImageRecyclerViewAdapter.ImageRecyclerView>(diffCallbackInt) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecyclerView {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ImageRecyclerView, position: Int) {
        holder.bind(getItem(position))

    }
    inner class ImageRecyclerView constructor(private val binding: ProductDetailsImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (image: String ){
            binding.image=image

            binding.executePendingBindings()
        }
        init {

        }

    }
    private fun from(parent: ViewGroup): ImageRecyclerView {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductDetailsImageItemBinding.inflate(inflater, parent, false)
        return ImageRecyclerView(binding)
    }
}

