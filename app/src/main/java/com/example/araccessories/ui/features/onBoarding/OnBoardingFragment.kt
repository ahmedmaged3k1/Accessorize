package com.example.araccessories.ui.features.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
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
        getStartedButton()
        return binding.root
    }
    private fun getStartedButton(){

    }

    private fun setOnboardingItems() {
        val onboardingItems: List<OnboardingItem> = listOf(
            OnboardingItem(
                onBoardingImage = R.drawable.onboarding1,
                "Your New Stylist,",

            ),
            OnboardingItem(
                onBoardingImage = R.drawable.onboarding2,
                "Your Best Friend,",

            ),


            OnboardingItem(
                onBoardingImage = R.drawable.onboarding3,
                "Discover a new you!",

            )
        )
        onboardingItemAdapter = OnboardingItemAdapter(onboardingItems)
        val onboardingViewPager = binding.onBoardingViewPager
        onboardingViewPager.adapter = onboardingItemAdapter

    }
}