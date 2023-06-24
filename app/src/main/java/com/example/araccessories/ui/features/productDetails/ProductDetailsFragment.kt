package com.example.araccessories.ui.features.productDetails

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentProductDetailsBinding
import com.example.araccessories.ui.core.utilities.NotificationUtils
import com.example.araccessories.ui.features.productDetails.adapters.ProductImageRecyclerViewAdapter
import com.example.araccessories.ui.features.signIn.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.ByteArrayOutputStream
import java.util.Locale

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(), java.io.Serializable {
    private lateinit var binding: FragmentProductDetailsBinding
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val productRecyclerViewAdapter = ProductImageRecyclerViewAdapter()
    private lateinit var imageList: List<Int>
    private val viewModel: ProductDetailsViewModel by viewModels()
    private val sharedViewModel: SignInViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        binding.product = viewModel
        binding.lifecycleOwner = this

        backButton()
        initializeArgs()
        addToCart()
        shareImage(requireContext(), args.products.images[0])
        tryOnProduct()
        initializeProductDetailsRecyclerView()

        return binding.root
    }

    private fun initializeArgs() {
        viewModel.productName.value = args.products.name
        viewModel.productDescription.value = args.products.description
        viewModel.productPrice.value = args.products.price.toString()
    }

    private fun backButton() {
        binding.backDetails.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_productDetailsFragment_to_mainNavigation)

        }
    }

    private fun shareImage(context: Context, imageUrL: String) {
        binding.shareDetails.setOnClickListener {
            viewModel.shareImage(context, imageUrL)

        }

    }

    private fun initializeProductDetailsRecyclerView() {
        productRecyclerViewAdapter.submitList(args.products.images)
        binding.productDetailsRecyclerView.adapter = productRecyclerViewAdapter
    }

    //Local Run
    private fun initializeImageList() {
        imageList = listOf(
            R.drawable.sunglasses,
            R.drawable.sunglasses,
            R.drawable.sunglasses,
            R.drawable.sunglasses
        )
    }

    private fun addToHistory() {
        viewModel.addToHistoryProduct(args.products)
    }

    private fun addToCart() {
        binding.addToCartButton.setOnClickListener {
            if (viewModel.checkIfAddedToCart(sharedViewModel.userData.email,args.products))
            {
                viewModel.addToCartProduct(args.products)
                // NotificationUtils.showNotification(requireContext(), "Cart", "Product Added To Cart")
                MotionToast.darkToast(
                    requireActivity(),
                    duration = MotionToast.LONG_DURATION,
                    position = MotionToast.GRAVITY_BOTTOM,
                    font = ResourcesCompat.getFont(
                        requireContext(),
                        www.sanju.motiontoast.R.font.helvetica_regular
                    ),
                    style = MotionToastStyle.SUCCESS,
                    message = "Product Added To Cart",
                    title = "Hey"
                )
            }
            else {
                MotionToast.darkToast(
                    requireActivity(),
                    duration = MotionToast.LONG_DURATION,
                    position = MotionToast.GRAVITY_BOTTOM,
                    font = ResourcesCompat.getFont(
                        requireContext(),
                        www.sanju.motiontoast.R.font.helvetica_regular
                    ),
                    style = MotionToastStyle.WARNING,
                    message = "Product is already in the cart , Check it again",
                    title = "Hey"
                )
            }


        }
    }


    private fun tryOnProduct() {
        binding.tryOnButton.setOnClickListener {
            addToHistory()
            if (args.products.category.lowercase(Locale.getDefault()) == "glasses") {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToGlassesTryOn(
                        args.products
                    )

                binding.root.findNavController()
                    .navigate(action)
            } else if (args.products.category.lowercase(Locale.getDefault()) == "hats") {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToHatsTryOnFragment(
                        args.products
                    )

                binding.root.findNavController()
                    .navigate(action)
            } else if (args.products.category.lowercase(Locale.getDefault()) == "makeup") {
                val action =
                    ProductDetailsFragmentDirections.actionProductDetailsFragmentToMakeUpTryOnFragment(
                        args.products
                    )

                binding.root.findNavController()
                    .navigate(action)
            } else if (args.products.category.lowercase(Locale.getDefault()) == "masks") {
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
