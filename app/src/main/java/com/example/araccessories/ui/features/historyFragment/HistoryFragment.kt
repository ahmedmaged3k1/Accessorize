package com.example.araccessories.ui.features.historyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.entities.Position
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.localDataSource.entities.Scale
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.databinding.FragmentHistoryBinding
import com.example.araccessories.ui.features.historyFragment.adapters.HistoryRecyclerViewAdapter
import com.example.araccessories.ui.features.signIn.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class HistoryFragment : Fragment()  , HistoryRecyclerViewAdapter.ProductRemoveClickListener {
    private lateinit var binding: FragmentHistoryBinding
    private val historyRecyclerViewAdapter = HistoryRecyclerViewAdapter(this)
    private lateinit var productList: List<Products>
    private val viewModel: HistoryFragmentViewModel by viewModels()
    private val sharedViewModel: SignInViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.cartRecyclerView.visibility=View.INVISIBLE
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility=View.VISIBLE
        initializeProductsHist(sharedViewModel.userData.email)
        return binding.root
    }

    private fun initializeProducts(){
        productList = listOf(
            Products("mask.sfb","Mask",listOf(R.drawable.mask1,R.drawable.mask2,R.drawable.mask3),450.0,3.0,4,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(2f, 2f, 2f), Position(0f, -0.005f, 0.017f)),
            Products("clownMask.sfb","Clown Mask",listOf(R.drawable.cm1,R.drawable.cm2,R.drawable.cm3),450.0,3.0,4,0,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(6f, 6f, 6f), Position(0f, -0.005f, 0.017f)),
            Products("maskamirat.sfb","Queen Mask",listOf(R.drawable.queen1,R.drawable.queen2,R.drawable.queen3),450.0,3.0,4,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(7f, 7f, 7f), Position(0f, +0.075f, 0.012f)),
            Products("2","Fox Make Up",listOf(R.drawable.fox_face_mesh_texture,R.drawable.fox_face_mesh_texture,R.drawable.fox_face_mesh_texture),450.0,3.0,3,0,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),

            )
    }
    private fun initializeProductsHist(userEmail : String) {
        viewModel.getAllProducts(userEmail)
        viewModel.productList.observe(viewLifecycleOwner){

            historyRecyclerViewAdapter.submitList(viewModel.productList.value)
            binding.cartRecyclerView.adapter=historyRecyclerViewAdapter
            binding.shimmerFrameLayout.stopShimmer()
            binding.cartRecyclerView.visibility=View.VISIBLE
            binding.shimmerFrameLayout.visibility=View.INVISIBLE
        }
    }

    override fun onProductRemove(products: ProductsRemote) {
        products.isTried=false
        viewModel.deleteProductFromCart(products)
        MotionToast.darkToast(requireActivity(),
            duration = MotionToast.LONG_DURATION,
            position = MotionToast.GRAVITY_BOTTOM,
            font = ResourcesCompat.getFont(requireContext(),www.sanju.motiontoast.R.font.helvetica_regular),
            style = MotionToastStyle.SUCCESS,
            message = "Deleted From History",
            title = "Item"
        )
        initializeProductsHist(sharedViewModel.userData.email)

    }

}