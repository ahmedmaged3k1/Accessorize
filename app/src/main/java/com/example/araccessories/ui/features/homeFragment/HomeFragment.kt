package com.example.araccessories.ui.features.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Ad
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Category
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Products
import com.example.araccessories.databinding.FragmentHomeBinding
import com.example.araccessories.ui.features.homeFragment.adapters.AdsRecyclerViewAdapter
import com.example.araccessories.ui.features.homeFragment.adapters.CategoryRecyclerViewAdapter
import com.example.araccessories.ui.features.homeFragment.adapters.ProductsRecyclerViewAdapter
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adList: List<Ad>
    private lateinit var categoryList: List<Category>
    private lateinit var productList: List<Products>

    private val adsRecyclerViewAdapter = AdsRecyclerViewAdapter()
    private val categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter()
    private val productRecyclerViewAdapter = ProductsRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initializeAdsRecyclerView()
        initializeCategoriesRecyclerView()
        initializeProductsRecyclerView()
        return binding.root
    }
    private fun initializeAdsRecyclerView(){
        initializeAds()
        val layoutManager = LinearLayoutManager(this.activity,LinearLayoutManager.HORIZONTAL,false)
        binding.appAds.layoutManager=layoutManager
        adsRecyclerViewAdapter.submitList(adList)
        binding.appAds.adapter=adsRecyclerViewAdapter
        val timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                if (layoutManager.findLastCompletelyVisibleItemPosition()<(adsRecyclerViewAdapter.itemCount -1 ))
                {
                    layoutManager.smoothScrollToPosition(binding.appAds,RecyclerView.State(),layoutManager.findLastCompletelyVisibleItemPosition()+1)
                } else if (layoutManager.findLastCompletelyVisibleItemPosition()<=(adsRecyclerViewAdapter.itemCount -1 ))
                {
                    layoutManager.smoothScrollToPosition(binding.appAds,RecyclerView.State(),0)
                }
            }
        },0,2000)
    }
    private fun initializeAds(){
        adList= listOf(Ad("Huge Sales on Accessories",R.drawable.sale),
            Ad("You Must Buy Now",R.drawable.sales2),
            Ad("Buy 2 Get 1 ",R.drawable.images_2)
            )
    }
    private fun initializeCategoriesRecyclerView(){
        initializeCategories()
        categoryRecyclerViewAdapter.submitList(categoryList)
        binding.categoriesRecyclerView.adapter=categoryRecyclerViewAdapter
    }
    private fun initializeCategories(){
        categoryList = listOf(
            Category("Glasses"),
                    Category("Masks"),
            Category("Makeup"),
            Category("Hats"),
            Category("Earings")
        )
    }
    private fun initializeProducts(){
        productList = listOf(Products(1,"Black Sunglasses"
            ,listOf(R.drawable.sunglasses_sdads,R.drawable.sunglasses_sdads),250.0,2.0,1,
            1,null,"The Best Sunglasses you can try on , " +
                    "ZARA company provides you this sunglasses and gives you 14 days return back even after you try it"),
            Products(1,"Sunglasses",
                listOf(R.drawable.sunglasses_sdads,R.drawable.sunglasses_sdads),250.0,
                2.0,1,0,null,
                "The Best Sunglasses you can try on , ZARA company provides " +
                        "you this sunglasses and gives you 14 days return back even after you try it"),
            Products(1,"Sunglasses",listOf(R.drawable.sunglasses_sdads,R.drawable.sunglasses_sdads),250.0,
                2.0,1,0,null,"The Best Sunglasses you " +
                        "can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it"),
            Products(1,"Sunglasses", listOf(R.drawable.sunglasses_sdads,R.drawable.sunglasses_sdads),250.0,2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it"),
            Products(1,"Sunglasses",listOf(R.drawable.sunglasses_sdads,R.drawable.sunglasses_sdads),250.0,2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it"),
            Products(1,"Sunglasses",listOf(R.drawable.sunglasses_sdads,R.drawable.sunglasses_sdads),250.0,2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it"),
            Products(1,"Sunglasses",listOf(R.drawable.sunglasses_sdads,R.drawable.sunglasses_sdads),250.0,2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it"))


    }
    private fun initializeProductsRecyclerView(){
        initializeProducts()
        productRecyclerViewAdapter.submitList(productList)
        binding.productsHomeRecyclerView.adapter=productRecyclerViewAdapter
    }



}