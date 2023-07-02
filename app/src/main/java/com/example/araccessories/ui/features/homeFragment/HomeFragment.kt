package com.example.araccessories.ui.features.homeFragment

import android.content.ContentValues.TAG
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.entities.Ad
import com.example.araccessories.data.dataSource.localDataSource.entities.Category
import com.example.araccessories.data.dataSource.localDataSource.entities.Position
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.localDataSource.entities.Scale
import com.example.araccessories.data.dataSource.localDataSource.sharedPrefrence.SharedPreference
import com.example.araccessories.data.dataSource.remoteDataSource.entities.ProductsRemote
import com.example.araccessories.databinding.FragmentHomeBinding
import com.example.araccessories.ui.features.homeFragment.adapters.AdsRecyclerViewAdapter
import com.example.araccessories.ui.features.homeFragment.adapters.CategoryRecyclerViewAdapter
import com.example.araccessories.ui.features.homeFragment.adapters.ProductsRecyclerViewAdapter
import com.example.araccessories.ui.features.signIn.SignInViewModel
import com.google.ar.sceneform.rendering.ModelRenderable
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class HomeFragment : Fragment(), ProductsRecyclerViewAdapter.ProductFavClickListener , CategoryRecyclerViewAdapter.CategoryClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adList: List<Ad>
    private lateinit var categoryList: List<Category>
    private lateinit var productList: List<Products>
    private val adsRecyclerViewAdapter = AdsRecyclerViewAdapter()
    private val categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(this)
    private val productRecyclerViewAdapter = ProductsRecyclerViewAdapter(this)
    private val viewModel: HomeFragmentViewModel by viewModels()
    private val sharedViewModel: SignInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.productsHomeRecyclerView.visibility=View.INVISIBLE
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility=View.VISIBLE

        welcomeName()
        initializeAdsRecyclerView()
        initializeCategoriesRecyclerView()
        initializeProductsRecyclerView()
        searchForProduct()

        return binding.root
    }

    private fun initializeProductsRemote() {
        viewModel.getAllProducts(
            "Bearer ${
                SharedPreference.readStringFromSharedPreference("token", "").toString()
            }", sharedViewModel.userData.email
        )
        //viewModel.saveAllCartProducts()

        viewModel.productList.observe(viewLifecycleOwner) {
            productRecyclerViewAdapter.submitList(viewModel.productList.value)
            binding.productsHomeRecyclerView.adapter = productRecyclerViewAdapter
            binding.shimmerFrameLayout.stopShimmer()
            binding.productsHomeRecyclerView.visibility=View.VISIBLE
            binding.shimmerFrameLayout.visibility=View.INVISIBLE



        }
    }

    private fun initializeAdsRecyclerView() {
        initializeAds()
        val layoutManager =
            LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false)
        binding.appAds.layoutManager = layoutManager
        adsRecyclerViewAdapter.submitList(adList)
        binding.appAds.adapter = adsRecyclerViewAdapter
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (layoutManager.findLastCompletelyVisibleItemPosition() < (adsRecyclerViewAdapter.itemCount - 1)) {
                    layoutManager.smoothScrollToPosition(
                        binding.appAds,
                        RecyclerView.State(),
                        layoutManager.findLastCompletelyVisibleItemPosition() + 1
                    )
                } else if (layoutManager.findLastCompletelyVisibleItemPosition() <= (adsRecyclerViewAdapter.itemCount - 1)) {
                    layoutManager.smoothScrollToPosition(binding.appAds, RecyclerView.State(), 0)
                }
            }
        }, 0, 2000)
    }

    private fun initializeAds() {
        adList = listOf(
            Ad("Huge Sales on Accessories", R.drawable.sale),
            Ad("You Must Buy Now", R.drawable.sales2),
            Ad("Buy 2 Get 1 ", R.drawable.images_2)
        )
    }

    private fun initializeCategoriesRecyclerView() {
        initializeCategories()
        categoryRecyclerViewAdapter.submitList(categoryList)
        binding.categoriesRecyclerView.adapter = categoryRecyclerViewAdapter
    }

    private fun initializeCategories() {
        categoryList = listOf(
            Category("Glasses", 1),
            Category("Masks", 2),
            Category("Makeup", 3),
            Category("Hats", 4),
            Category("Ears", 5)
        )
    }

    private fun initializeProductsRecyclerView() {
        //  initializeProducts()
        initializeProductsRemote()
        // productRecyclerViewAdapter.submitList(viewModel.productList.value)
        //   binding.productsHomeRecyclerView.adapter=productRecyclerViewAdapter

    }

    private fun welcomeName() {
        binding.homeWelcome.text = buildString {
            append("Welcome ")
            append(sharedViewModel.userData.firstName)
        }
    }

    // For local and offline try

    override fun onProductAdd(position: Int) {

        viewModel.addToFavProduct(viewModel.productList.value!![position])
        MotionToast.darkToast(requireActivity(),
            duration = MotionToast.LONG_DURATION,
            position = MotionToast.GRAVITY_BOTTOM,
            font = ResourcesCompat.getFont(requireContext(),www.sanju.motiontoast.R.font.helvetica_regular),
            style = MotionToastStyle.SUCCESS,
            message = "Added To Fav",
            title = "Item"
        )


    }

    override fun onProductRemove(position: Int) {
        viewModel.deleteFromFavProduct(viewModel.productList.value!![position])
        MotionToast.darkToast(requireActivity(),
            duration = MotionToast.LONG_DURATION,
            position = MotionToast.GRAVITY_BOTTOM,
            font = ResourcesCompat.getFont(requireContext(),www.sanju.motiontoast.R.font.helvetica_regular),
            style = MotionToastStyle.SUCCESS,
            message = "Removed From Fav",
            title = "Item"
        )


    }


    override fun onCategoryClick(category: String) {
        viewModel.filteredProductList.postValue(viewModel.productList.value?.filter { it.category == category } as ArrayList<ProductsRemote>)
        viewModel.filteredProductList.observe(viewLifecycleOwner) {
            productRecyclerViewAdapter.submitList(viewModel.filteredProductList.value)
            binding.productsHomeRecyclerView.adapter = productRecyclerViewAdapter
        }

    }
    private fun searchForProduct(){
        binding.homeSearch.clearFocus()
        binding.homeSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false

            }

            override fun onQueryTextChange(newText: String): Boolean {
                filerProducts(newText)
                return true
            }

        })
    }
    private fun filerProducts(text : String){
        val filteredList = mutableListOf<ProductsRemote>()
        viewModel.productList.value?.forEach {
            if (it.name.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(it)
                Log.d(TAG, "filerProducts: add data")
            }
        }
        if (filteredList.isEmpty())
        {


            MotionToast.darkToast(
                requireActivity(),
                duration = MotionToast.SHORT_DURATION,
                position = MotionToast.GRAVITY_BOTTOM,
                font = ResourcesCompat.getFont(
                    requireContext(),
                    www.sanju.motiontoast.R.font.helvetica_regular
                ),
                style = MotionToastStyle.WARNING,
                message = "No Data Found",
                title = "Sorry"
            )
        }
        else{
            productRecyclerViewAdapter.submitList(filteredList)
            binding.productsHomeRecyclerView.adapter = productRecyclerViewAdapter
        }
    }




}