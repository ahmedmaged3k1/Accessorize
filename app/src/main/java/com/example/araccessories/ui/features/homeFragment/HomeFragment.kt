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
            Category("Earings", 5)
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
    private fun initializeProducts() {
        productList = listOf(
            Products(
                "1",
                "Yellow Glasses",
                listOf(R.drawable.y1, R.drawable.y2, R.drawable.y3),
                250.0,
                2.0,
                1,
                0,
                null,
                "The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                null,
                null
            ),
            Products(
                "2",
                "Purple Rouge",
                listOf(
                    R.drawable.makeup2,
                    R.drawable.purplerouge1,
                    R.drawable.purplerouge2,
                    R.drawable.purplerouge3
                ),
                450.0,
                3.0,
                3,
                1,
                null,
                "The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                Scale(0.09f, 0.07f, 0.09f),
                Position(0.09f, 0.07f, 0.09f)
            ),
            Products(
                "1",
                "Sunglasses",
                listOf(R.drawable.g1, R.drawable.g2),
                375.0,
                2.0,
                1,
                0,
                null,
                "The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                null,
                null
            ),
            Products(
                "hat.sfb",
                "Hat",
                listOf(R.drawable.h1, R.drawable.h2, R.drawable.h3, R.drawable.h4),
                450.0,
                3.0,
                2,
                1,
                null,
                "The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                Scale(0.09f, 0.07f, 0.09f),
                Position(0.09f, 0.07f, 0.09f)
            ),
            Products(
                "2",
                "Red Rouge",
                listOf(R.drawable.makeup, R.drawable.redrouge1, R.drawable.redrouge2),
                450.0,
                3.0,
                3,
                1,
                null,
                "The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                Scale(0.09f, 0.07f, 0.09f),
                Position(0.09f, 0.07f, 0.09f)
            ),
            Products(
                "mask.sfb",
                "Mask",
                listOf(R.drawable.mask1, R.drawable.mask2, R.drawable.mask3),
                450.0,
                3.0,
                4,
                1,
                null,
                "The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                Scale(2f, 2f, 2f),
                Position(0f, -0.005f, 0.017f)
            ),
            Products(
                "clownMask.sfb",
                "Clown Mask",
                listOf(R.drawable.cm1, R.drawable.cm2, R.drawable.cm3),
                450.0,
                3.0,
                4,
                0,
                null,
                "The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                Scale(6f, 6f, 6f),
                Position(0f, -0.005f, 0.017f)
            ),
            Products(
                "maskamirat.sfb",
                "Queen Mask",
                listOf(R.drawable.queen1, R.drawable.queen2, R.drawable.queen3),
                450.0,
                3.0,
                4,
                1,
                null,
                "The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                Scale(7f, 7f, 7f),
                Position(0f, +0.075f, 0.012f)
            ),
            Products(
                "2",
                "Fox Make Up",
                listOf(
                    R.drawable.fox_face_mesh_texture,
                    R.drawable.fox_face_mesh_texture,
                    R.drawable.fox_face_mesh_texture
                ),
                450.0,
                3.0,
                3,
                0,
                null,
                "The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",
                Scale(0.09f, 0.07f, 0.09f),
                Position(0.09f, 0.07f, 0.09f)
            ),

            )
        initializingModelRenderable()
    }

    override fun onProductAdd(position: Int) {

        viewModel.addToFavProduct(viewModel.productList.value!![position])


    }

    override fun onProductRemove(position: Int) {
        viewModel.deleteFromFavProduct(viewModel.productList.value!![position])

    }

    private fun initializingModelRenderable() {
        ModelRenderable.builder()
            .setSource(this.activity, Uri.parse("shady2.sfb"))
            .build()
            .thenAccept { modelRenderable ->
                productList[2].productModel = modelRenderable
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false

            }
        ModelRenderable.builder()
            .setSource(this.activity, Uri.parse("yellow_sunglasses.sfb"))
            .build()
            .thenAccept { modelRenderable ->
                productList[0].productModel = modelRenderable
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false

            }

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
            Log.d(TAG, "filerProducts: no data")
            Toast.makeText(requireContext(),"No Data Found",Toast.LENGTH_SHORT).show()
        }
        else{
            productRecyclerViewAdapter.submitList(filteredList)
            binding.productsHomeRecyclerView.adapter = productRecyclerViewAdapter
        }
    }




}