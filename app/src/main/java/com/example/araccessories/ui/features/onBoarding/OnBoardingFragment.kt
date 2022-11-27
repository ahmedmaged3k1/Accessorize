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
                "Face Accessories Try - On",
                "You can try on the glasses you want and see how it looks on your face "
            ),
            OnboardingItem(
                onBoardingImage = R.drawable.onboard_2,
                "Future Of E-commerce ",
                "Through our app you can try on any product before you buy it using augmented reality"
            ),


            OnboardingItem(
                onBoardingImage = R.drawable.ic_baseline_camera_front_24,
                "Take A Shot ",
                "You can take a shot to send it to your friend while trying on the product "
            )
        )
        onboardingItemAdapter = OnboardingItemAdapter(onboardingItems)
        val onboardingViewPager = binding.onBoardingViewPager
        onboardingViewPager.adapter = onboardingItemAdapter

    }
}