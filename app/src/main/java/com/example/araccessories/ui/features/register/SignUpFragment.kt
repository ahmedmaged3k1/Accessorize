package com.example.araccessories.ui.features.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.findNavController
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentSignUpBinding
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        login()
        return binding.root
    }
    private  fun login(){
        binding.tvSignUp.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signUpFragment_to_signInFragment)

        }
    }


}