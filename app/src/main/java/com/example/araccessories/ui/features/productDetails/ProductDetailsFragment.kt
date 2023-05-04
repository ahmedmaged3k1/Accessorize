package com.example.araccessories.ui.features.productDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentProductDetailsBinding
import com.example.araccessories.ui.core.utilities.NotificationUtils
import com.example.araccessories.ui.features.productDetails.adapters.ProductImageRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() , java.io.Serializable{
    private lateinit var binding: FragmentProductDetailsBinding
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val productRecyclerViewAdapter = ProductImageRecyclerViewAdapter()
    private lateinit var imageList :List<Int>
    private val viewModel: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        binding.product=viewModel
        binding.lifecycleOwner=this
        backButton()
        initializeArgs()
        addToCart()
      //  initializeImageList()

        tryOnProduct()
        initializeProductDetailsRecyclerView()


        return binding.root
    }
    private fun initializeArgs(){
        viewModel.productName.value = args.products.name
        viewModel.productDescription.value = args.products.description
        viewModel.productPrice.value = args.products.price.toString()
    }
     private fun backButton(){
        binding.backDetails.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_productDetailsFragment_to_mainNavigation)

        }
    }
    private fun initializeProductDetailsRecyclerView(){
        productRecyclerViewAdapter.submitList(args.products.images)
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
    private fun addToHistory(){
        viewModel.addToHistoryProduct(args.products)
    }
    private fun addToCart(){
        binding.addToCartButton.setOnClickListener{
            viewModel.addToCartProduct(args.products)
            NotificationUtils.showNotification(requireContext(), "Cart", "Product Added To Cart")

        }
    }


    private fun tryOnProduct(){
        binding.tryOnButton.setOnClickListener {
            addToHistory()
            if (args.products.category?.toLowerCase()=="glasses")
            {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToGlassesTryOn(
                        args.products
                    )

                binding.root.findNavController()
                    .navigate(action)
            }
            else   if (args.products.category?.toLowerCase()=="hats")
            {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToHatsTryOnFragment(
                        args.products
                    )

                binding.root.findNavController()
                    .navigate(action)
            }
            else   if (args.products.category?.toLowerCase()=="makeup")
            {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToMakeUpTryOnFragment(
                        args.products
                    )

                binding.root.findNavController()
                    .navigate(action)
            }
            else   if (args.products.category?.toLowerCase()=="masks")
            {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToMasksTryOnFragment(
                        args.products
                    )

                binding.root.findNavController()
                    .navigate(action)
            }

        }
        }

    }
