package com.example.araccessories.ui.features.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentSignUpBinding
import com.example.araccessories.ui.core.HelperFunctions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.user=viewModel
        binding.lifecycleOwner=this
        if (HelperFunctions.isInternetConnected(requireContext()))
        {
            validateLogin()
            wrongCredentials()
            login(binding.root)
        }
        else{
            HelperFunctions.noInternetConnection(requireContext())
        }
        login()
        return binding.root
    }
    private  fun login(){
        binding.tvSignUp.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signUpFragment_to_signInFragment)

        }
    }
    private fun validateLogin() {
        viewModel.validatorSignUp.observe(viewLifecycleOwner) {
            if (it == 2) {
                Toast.makeText(requireContext(),"Please Fill All Fields",Toast.LENGTH_LONG).show()
            }

        }

    }
    private fun wrongCredentials() {
        viewModel.badRequestSignUp.observe(viewLifecycleOwner) {
            if (it == 2) {
                Toast.makeText(requireContext(),"Account Creation Error , Please Try Again",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun login(view: View) {
        viewModel.observerSignUp.observe(viewLifecycleOwner) {
            if (it == 2) {
                Toast.makeText(requireContext(),"Account Created Successfully",Toast.LENGTH_LONG).show()

                view.findNavController()
                    .navigate(
                        SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                    )
                binding.signIn.isClickable = false
            }
        }

    }


}