package com.example.araccessories.ui.features.signIn

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentSignInBinding
import com.example.araccessories.databinding.FragmentSplashBinding


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        signUpButton()
        return binding.root
    }
    private fun signUpButton(){
        binding.signIn.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

}