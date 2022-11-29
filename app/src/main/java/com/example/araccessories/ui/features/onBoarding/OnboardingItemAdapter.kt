package com.example.araccessories.ui.features.onBoarding

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.R

class OnboardingItemAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingItemAdapter.OnboardingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        return OnboardingItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.onboarding_item_container, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    inner class OnboardingItemViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        private val imageOnboarding = view.findViewById<ImageView>(R.id.imageOnBoarding)
        private val title = view.findViewById<TextView>(R.id.onBoardingTextTitle)
        fun bind(onboardingItem: OnboardingItem) {
            imageOnboarding.setImageResource(onboardingItem.onBoardingImage)
            title.text = onboardingItem.title

        }


    }


}