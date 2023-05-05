package com.example.araccessories.ui.features.homeFragment.adapters

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.araccessories.data.dataSource.localDataSource.entities.Category
import com.example.araccessories.databinding.CategoryItemBinding

class CategoryRecyclerViewAdapter(private val listener: CategoryClickListener) :
    ListAdapter<Category, CategoryRecyclerViewAdapter.CategoryRecyclerView>(diffCallbackCategory) {

    private var clickedPosition = -1 // To keep track of the clicked item position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecyclerView {
        return from(parent)
    }

    override fun onBindViewHolder(holder: CategoryRecyclerView, position: Int) {
        holder.bind(getItem(position), position)
    }

    interface CategoryClickListener {
        fun onCategoryClick(category: String)
    }

    inner class CategoryRecyclerView(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, position: Int) {
            binding.category = category

            if (position == clickedPosition) { // If this item is the clicked item
                val drawable = ContextCompat.getDrawable(
                    binding.root.context,
                    com.example.araccessories.R.drawable.category_item_not_clicked
                )
                binding.categoryName.background = drawable
            } else {
                val drawable = ContextCompat.getDrawable(
                    binding.root.context,
                    com.example.araccessories.R.drawable.category_item_shape
                )
                binding.categoryName.background = drawable
            }

            binding.categoryName.setOnClickListener {
                clickedPosition = position // Update the clicked item position

                notifyDataSetChanged() // Notify adapter to update all items' background color

                listener.onCategoryClick(category.categoryName)
            }

            binding.executePendingBindings()
        }
    }

    companion object {
        val diffCallbackCategory = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.categoryName == newItem.categoryName
            }
        }
    }

    fun from(parent: ViewGroup): CategoryRecyclerView {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return CategoryRecyclerView(binding)
    }
}
