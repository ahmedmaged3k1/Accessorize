package com.example.araccessories.ui.features.productDetails

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentProductDetailsBinding
import com.example.araccessories.databinding.FragmentSplashBinding


class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private val args by navArgs<ProductDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        binding.productItemDetailsName.text = args.product.productName
        binding.productDetailsPrice.text = "${args.product.productPrice} Egp"
        binding.productDescription.text=args.product.productDescription


        return binding.root
    }

}