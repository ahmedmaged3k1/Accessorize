package com.example.araccessories.ui.features.welcomeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.welcomeFragmentLayout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_welcomeFragment_to_onBoardingFragment)
        }
        return binding.root
    }


}