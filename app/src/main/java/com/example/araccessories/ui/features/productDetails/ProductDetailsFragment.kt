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
import com.example.araccessories.ui.features.homeFragment.adapters.ProductsRecyclerViewAdapter
import com.example.araccessories.ui.features.productDetails.adapters.ProductImageRecyclerViewAdapter


class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val productRecyclerViewAdapter = ProductImageRecyclerViewAdapter()
    private lateinit var imageList :List<Int>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        backButton()
        initializeArgs()
        initializeImageList()

        initializeProductDetailsRecyclerView()


        return binding.root
    }
    private fun initializeArgs(){
        binding.productItemDetailsName.text = args.product.productName
        binding.productDetailsPrice.text = "${args.product.productPrice} Egp"
        binding.productDescription.text=args.product.productDescription
    }
     private fun backButton(){
        binding.backDetails.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_productDetailsFragment_to_mainNavigation)

        }
    }
    private fun initializeProductDetailsRecyclerView(){
        productRecyclerViewAdapter.submitList(args.product.productImage)
        binding.productDetailsRecyclerView.adapter=productRecyclerViewAdapter
    }
    private fun initializeImageList()
    {
        imageList= listOf(R.drawable.sunglasses,
            R.drawable.sunglasses,
            R.drawable.sunglasses,
            R.drawable.sunglasses
            )
    }
}