package com.example.araccessories.ui.features.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentSignUpBinding
import com.example.araccessories.ui.core.HelperFunctions
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

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

                MotionToast.darkToast(requireActivity(),
                    duration = MotionToast.LONG_DURATION,
                    position = MotionToast.GRAVITY_BOTTOM,
                    font = ResourcesCompat.getFont(requireContext(),www.sanju.motiontoast.R.font.helvetica_regular),
                    style = MotionToastStyle.WARNING,
                    message = "Please Fill All Fields",
                    title = "Hey"
                )
            }

        }

    }
    private fun wrongCredentials() {
        viewModel.badRequestSignUp.observe(viewLifecycleOwner) {
            if (it == 2) {
                MotionToast.darkToast(requireActivity(),
                    duration = MotionToast.LONG_DURATION,
                    position = MotionToast.GRAVITY_BOTTOM,
                    font = ResourcesCompat.getFont(requireContext(),www.sanju.motiontoast.R.font.helvetica_regular),
                    style = MotionToastStyle.ERROR,
                    message = "Account Creation Error , Please Try Again",
                    title = "Hey"
                )
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
                MotionToast.darkToast(requireActivity(),
                    duration = MotionToast.LONG_DURATION,
                    position = MotionToast.GRAVITY_BOTTOM,
                    font = ResourcesCompat.getFont(requireContext(),www.sanju.motiontoast.R.font.helvetica_regular),
                    style = MotionToastStyle.SUCCESS,
                    message = "Account Created Successfully",
                    title = "Hey"
                )
                binding.signIn.isClickable = false
            }
        }

    }


}