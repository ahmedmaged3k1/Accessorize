package com.example.araccessories.ui.features.productDetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.adapters.ImageViewBindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Category
import com.example.araccessories.databinding.ProductDetailsImageItemBinding


val diffCallbackInt= object : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem


    }



}
class ProductImageRecyclerViewAdapter :
    ListAdapter<Int, ProductImageRecyclerViewAdapter.ImageRecyclerView>(diffCallbackInt) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecyclerView {
        return from(parent)
    }

    override fun onBindViewHolder(holder: ImageRecyclerView, position: Int) {
        holder.bind(getItem(position))

    }
    inner class ImageRecyclerView constructor(private val binding: ProductDetailsImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (image :Int){
            binding.imageDetails=image
            binding.imageDetailsRecyclerView.setImageResource(getItem(position))
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

