package com.example.araccessories.ui.features.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentOnBoardingBinding


class OnBoardingFragment : Fragment() {
    private lateinit var onboardingItemAdapter: OnboardingItemAdapter
    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        setOnboardingItems()
        return binding.root
    }

    private fun setOnboardingItems() {
        val onboardingItems: List<OnboardingItem> = listOf(
            OnboardingItem(
                onBoardingImage = R.drawable.onboard_1,
                "Face Accessories Try - On"
            ),
            OnboardingItem(
                onBoardingImage = R.drawable.onboard_2,
                "Future Of E-commerce "

            ),


            OnboardingItem(
                onBoardingImage = R.drawable.shot_me,
                "Take A Shot "
            )
        )
        onboardingItemAdapter = OnboardingItemAdapter(onboardingItems)
        val onboardingViewPager = binding.onBoardingViewPager
        onboardingViewPager.adapter = onboardingItemAdapter

    }
}