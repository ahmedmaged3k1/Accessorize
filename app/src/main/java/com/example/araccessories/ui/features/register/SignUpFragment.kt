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
        fillProfileButton()
        setEmailFieldFocus()
        setPasswordFieldFocus()
        return binding.root
    }
    private fun validateLogin(){
        binding.signUp.setOnClickListener{
            if(emailSignIn.text.toString() == "" || passwordSignIn.text.toString() == ""){
                Toast.makeText(requireContext(),"Fill All Fields ", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }}
    }
    private fun fillProfileButton(){
        validateLogin()
        binding.signUp.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_signUpFragment_to_fillProfileFragment)
        }

    }

    private fun setEmailFieldFocus(){
        binding.emailSignUp.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                DrawableCompat.setTint(
                    DrawableCompat.wrap(emailSignUp.compoundDrawables[0]),
                    ContextCompat.getColor(requireContext(), R.color.black)
                )
            } else {
                DrawableCompat.setTint(
                    DrawableCompat.wrap(emailSignUp.compoundDrawables[0]),
                    ContextCompat.getColor(requireContext(), R.color.grey)
                )
            }
        }
    }
    private fun setPasswordFieldFocus(){
        binding.passwordSignUp.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                DrawableCompat.setTint(
                    DrawableCompat.wrap(passwordSignUp.compoundDrawables[0]),
                    ContextCompat.getColor(requireContext(), R.color.black)
                )
            } else {
                DrawableCompat.setTint(
                    DrawableCompat.wrap(passwordSignUp.compoundDrawables[0]),
                    ContextCompat.getColor(requireContext(), R.color.grey)
                )
            }
        }

    }
}