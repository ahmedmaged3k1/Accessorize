package com.example.araccessories.ui.features.homeFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.localDataSource.entities.Category
import com.example.araccessories.databinding.CategoryItemBinding

val diffCallbackCategory = object : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.categoryName == newItem.categoryName


    }



}
class CategoryRecyclerViewAdapter :
    ListAdapter<Category, CategoryRecyclerViewAdapter.CategoryRecyclerView>(diffCallbackCategory) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecyclerView {
        return from(parent)
    }

    override fun onBindViewHolder(holder: CategoryRecyclerView, position: Int) {
        holder.bind(getItem(position))
    }
    inner class CategoryRecyclerView constructor(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (category : Category){
            binding.category=category
            binding.executePendingBindings()
        }
        init {

        }

    }
    fun from(parent: ViewGroup): CategoryRecyclerView {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return CategoryRecyclerView(binding)
    }
}

