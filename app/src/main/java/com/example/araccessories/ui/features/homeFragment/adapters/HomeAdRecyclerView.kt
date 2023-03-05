package com.example.araccessories.ui.features.homeFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Ad
import com.example.araccessories.databinding.AdItemBinding

class AdsRecyclerViewAdapter :
    ListAdapter<Ad, AdsRecyclerViewAdapter.AdsViewHolder>(diffCallbackAd) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AdsViewHolder constructor(private val binding: AdItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ad: Ad) {
            binding.ad = ad
            binding.executePendingBindings()
        }

        init {

        }

    }

    fun from(parent: ViewGroup): AdsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdItemBinding.inflate(inflater, parent, false)
        return AdsViewHolder(binding)
    }
}

val diffCallbackAd = object : DiffUtil.ItemCallback<Ad>() {
    override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
        return oldItem.adName == newItem.adName


    }


}