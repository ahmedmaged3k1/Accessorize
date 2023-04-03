package com.example.araccessories.ui.features.homeFragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.remoteDataSource.entities.*
import com.example.araccessories.databinding.FragmentHomeBinding
import com.example.araccessories.ui.features.homeFragment.adapters.AdsRecyclerViewAdapter
import com.example.araccessories.ui.features.homeFragment.adapters.CategoryRecyclerViewAdapter
import com.example.araccessories.ui.features.homeFragment.adapters.ProductsRecyclerViewAdapter
import com.google.ar.sceneform.rendering.ModelRenderable
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
        productList = listOf(
            Products("1","Yellow Glasses",listOf(R.drawable.y1,R.drawable.y2,R.drawable.y3),250.0, 2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",null,null),
            Products("1","Sunglasses", listOf(R.drawable.g1,R.drawable.g2),375.0,2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",null,null),
            Products("hat.sfb","Hat",listOf(R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4),450.0,3.0,2,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            Products("2","Red Rouge",listOf(R.drawable.makeup,R.drawable.redrouge1,R.drawable.redrouge2),450.0,3.0,3,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            Products("2","Purple Rouge",listOf(R.drawable.makeup2,R.drawable.purplerouge1,R.drawable.purplerouge2,R.drawable.purplerouge3),450.0,3.0,3,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
           // Products("mask.sfb","Mask",listOf(R.drawable.makeup,R.drawable.makeup),450.0,3.0,4,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(2f, 2f, 2f), Position(0f, -0.005f, 0.017f)),
            Products("clownMask.sfb","Clown Mask",listOf(R.drawable.cm1,R.drawable.cm2,R.drawable.cm3),450.0,3.0,4,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(6f, 6f, 6f), Position(0f, -0.005f, 0.017f)),
            Products("maskamirat.sfb","Queen Mask",listOf(R.drawable.queen1,R.drawable.queen2,R.drawable.queen3),450.0,3.0,4,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(7f, 7f, 7f), Position(0f, +0.075f, 0.012f))






        )



        initializingModelRenderable()

    }
    private fun initializingModelRenderable(){

        ModelRenderable.builder()
            .setSource(this.activity, Uri.parse("shady2.sfb"))
            .build()
            .thenAccept { modelRenderable ->
                productList[0].productModel=modelRenderable
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false

            }
        ModelRenderable.builder()
            .setSource(this.activity, Uri.parse("yellow_sunglasses.sfb"))
            .build()
            .thenAccept { modelRenderable ->
                productList[1].productModel=modelRenderable
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false

            }

    }
    private fun initializeProductsRecyclerView(){
        initializeProducts()
        productRecyclerViewAdapter.submitList(productList)
        binding.productsHomeRecyclerView.adapter=productRecyclerViewAdapter
    }




}