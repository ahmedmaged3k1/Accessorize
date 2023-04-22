package com.example.araccessories.ui.features.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentSignInBinding
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        validateLogin()
        signUpButton()
        return binding.root
    }

    private fun validateLogin() {
        binding.signIn.setOnClickListener {
            if (emailSignUp.text.toString() == "" || nameSignUp.text.toString() == "") {
                Toast.makeText(requireContext(), "Fill All Fields ", Toast.LENGTH_LONG).show()
            }
            else {
                view?.findNavController()?.navigate(R.id.action_signInFragment_to_mainNavigation)

            }
        }
    }

    private fun signUpButton() {
        binding.tvSignUp.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }



}