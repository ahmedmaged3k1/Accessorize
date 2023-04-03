package com.example.araccessories.ui.features.onBoarding

import android.os.Bundle
import android.transition.Visibility
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
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
        skipButton()
        nextButton()
        onBoardFinished()
        getStartedButton()
        return binding.root
    }
    private fun getStartedButton(){
        binding.getStartedButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_onBoardingFragment_to_signInFragment)
        }
    }
    private fun skipButton(){
        binding.skipButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_onBoardingFragment_to_signInFragment)

        }
    }
    private fun nextButton(){
        binding.nextButton.setOnClickListener {
            binding.onBoardingViewPager.setCurrentItem(binding.onBoardingViewPager.currentItem+1,true)
        }
    }
    private fun onBoardFinished(){
        binding.onBoardingViewPager.registerOnPageChangeCallback(object :OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position==2)
                {
                    binding.skipButton.visibility=View.INVISIBLE
                    binding.nextButton.visibility = View.INVISIBLE
                    binding.getStartedButton.visibility=View.VISIBLE
                }
                else{
                    binding.skipButton.visibility=View.VISIBLE
                    binding.nextButton.visibility = View.VISIBLE
                    binding.getStartedButton.visibility=View.INVISIBLE
                }
            }
        })
    }


    private fun setOnboardingItems() {
        val onboardingItems: List<OnboardingItem> = listOf(
            OnboardingItem(
                onBoardingImage = R.drawable.onboarding2,
                "Your New Stylist,",

            ),
            OnboardingItem(
                onBoardingImage = R.drawable.onboarding3,
                "Your Best Friend,",

            ),


            OnboardingItem(
                onBoardingImage = R.drawable.onboarding1,
                "Discover a new you!",

            )
        )
        onboardingItemAdapter = OnboardingItemAdapter(onboardingItems)
        val onboardingViewPager = binding.onBoardingViewPager
        onboardingViewPager.adapter = onboardingItemAdapter

    }
}