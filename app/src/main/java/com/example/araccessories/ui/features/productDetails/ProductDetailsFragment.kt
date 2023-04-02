package com.example.araccessories.ui.features.productDetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.GlassesActivity
import com.example.araccessories.MainActivity
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentProductDetailsBinding
import com.example.araccessories.ui.features.mainNavigation.MainNavigationDirections
import com.example.araccessories.ui.features.productDetails.adapters.ProductImageRecyclerViewAdapter


class ProductDetailsFragment : Fragment() , java.io.Serializable{
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
        tryOnProduct()
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

    private fun tryOnProduct(){
        binding.tryOnButton.setOnClickListener {
            if (args.product.categoryId==1)
            {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToGlassesTryOn(
                        args.product
                    )

                binding.root.findNavController()
                    .navigate(action)
            }
            else   if (args.product.categoryId==2)
            {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToHatsTryOnFragment(
                        args.product
                    )

                binding.root.findNavController()
                    .navigate(action)
            }
            else   if (args.product.categoryId==3)
            {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToMakeUpTryOnFragment(
                        args.product
                    )

                binding.root.findNavController()
                    .navigate(action)
            }
            else   if (args.product.categoryId==4)
            {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToMasksTryOnFragment(
                        args.product
                    )

                binding.root.findNavController()
                    .navigate(action)
            }



        }
        }

    }
