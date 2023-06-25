package com.example.araccessories.ui.features.cartFragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.entities.Position
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.localDataSource.entities.Scale
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.databinding.FragmentCartBinding
import com.example.araccessories.ui.features.cartFragment.adapters.CartFragmentRecyclerView
import com.example.araccessories.ui.features.favouriteFragment.FavouriteViewModel
import com.example.araccessories.ui.features.signIn.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class CartFragment : Fragment(), CartFragmentRecyclerView.ProductCartClickListener {



    private lateinit var binding: FragmentCartBinding
    private val cartRecyclerViewAdapter = CartFragmentRecyclerView(this)
    private lateinit var productList: List<Products>
    private val viewModel: CartFragmentViewModel by viewModels()
    private val sharedViewModel: SignInViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        initializeProductsRecyclerView(sharedViewModel.userData.email)
        return binding.root
    }
    private fun initializeProductsRecyclerView(userEmail: String){
        viewModel.getAllProducts(userEmail)
        viewModel.productList.observe(viewLifecycleOwner){
            cartRecyclerViewAdapter.submitList(viewModel.productList.value)
            binding.cartRecyclerView.adapter=cartRecyclerViewAdapter
        }
     observerOrderTotal()
    }
    private fun observerOrderTotal()
    {
        viewModel.orderTotal.observe(viewLifecycleOwner){
            binding.orderTotal.text=viewModel.orderPrice.toString()
        }
    }
    private fun initializeProducts(){
        productList = listOf(
            Products("1","Yellow Glasses",listOf(R.drawable.y1,R.drawable.y2,R.drawable.y3),250.0, 2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",null,null),
            Products("1","Sunglasses", listOf(R.drawable.g1,R.drawable.g2),375.0,2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",null,null),
            Products("hat.sfb","Hat",listOf(R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4),450.0,3.0,2,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            Products("2","Red Rouge",listOf(R.drawable.makeup,R.drawable.redrouge1,R.drawable.redrouge2),450.0,3.0,3,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            Products("mask.sfb","Mask",listOf(R.drawable.mask1,R.drawable.mask2,R.drawable.mask3),450.0,3.0,4,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(2f, 2f, 2f), Position(0f, -0.005f, 0.017f)),

            )





    }

    @SuppressLint("SuspiciousIndentation")
    override fun onProductInc(position: Int) {
        //viewModel.increasePrice(position)
      viewModel.orderPrice +=  viewModel.productList.value?.get(position)!!.price
        viewModel.orderTotal.value?.inc()
        observerOrderTotal()

    }

    override fun onProductDec(position: Int) {
       // viewModel.decreasePrice(position)
        Log.d(TAG, "onProductDec: ${viewModel.orderPrice} ")
        viewModel.orderPrice -=  viewModel.productList.value?.get(position)!!.price
        Log.d(TAG, "onProductDec: ${viewModel.orderPrice} ")

        viewModel.orderTotal.value?.inc()
        observerOrderTotal()


    }

    override fun orderPrice(price: Int) {


    }

    override fun removeProduct(products: ProductsRemote) {
        products.isCart=false
        viewModel.deleteProductFromCart(products)
        MotionToast.darkToast(requireActivity(),
            duration = MotionToast.LONG_DURATION,
            position = MotionToast.GRAVITY_BOTTOM,
            font = ResourcesCompat.getFont(requireContext(),www.sanju.motiontoast.R.font.helvetica_regular),
            style = MotionToastStyle.SUCCESS,
            message = "Deleted From Cart",
            title = "Item"
        )
        initializeProductsRecyclerView(sharedViewModel.userData.email)

    }


}