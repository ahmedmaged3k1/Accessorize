package com.example.araccessories.ui.features.signIn

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.sharedPrefrence.SharedPreference
import com.example.araccessories.databinding.FragmentSignInBinding
import com.example.araccessories.ui.core.HelperFunctions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by activityViewModels()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.user=viewModel
        binding.lifecycleOwner=this
        binding.progressBar.visibility=View.INVISIBLE

        if (HelperFunctions.isInternetConnected(requireActivity().applicationContext))
            {
                progressBarController()
                validateLogin()
                wrongCredentials()
                login(binding.root)
                signUpButton()
            }
            else{
                HelperFunctions.noInternetConnection(requireActivity().applicationContext)
            }



        return binding.root
    }
    private fun login(view: View) {
        viewModel.observer.observe(viewLifecycleOwner) {

            if (it == 2) {
                view.findNavController()
                    .navigate(
                        SignInFragmentDirections.actionSignInFragmentToMainNavigation()
                    )
            }
        }

    }
    private fun validateLogin() {
        viewModel.validator.observe(viewLifecycleOwner) {
            if (it == 2) {

                Toast.makeText(requireContext(),"Please Fill All Fields",Toast.LENGTH_LONG).show()
            }

        }

    }
    private fun wrongCredentials() {
        viewModel.badRequest.observe(viewLifecycleOwner) {
            if (it == 2) {
                Toast.makeText(requireContext(),"Wrong Email Or Password",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun signUpButton() {
        binding.tvSignUp.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }
    private fun progressBarController(){

        viewModel.progressBar.observe(viewLifecycleOwner) {
            if (it == 2) {
                binding.progressBar.visibility=View.VISIBLE
                binding.imageButton.visibility=View.INVISIBLE
                binding.imageButton2.visibility=View.INVISIBLE
                binding.texastView5.visibility=View.INVISIBLE
            }
            else  {
                binding.progressBar.visibility=View.INVISIBLE
                binding.imageButton.visibility=View.VISIBLE
                binding.imageButton2.visibility=View.VISIBLE
                binding.texastView5.visibility=View.VISIBLE
            }
        }
    }



}