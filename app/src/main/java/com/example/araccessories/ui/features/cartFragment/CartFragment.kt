package com.example.araccessories.ui.features.cartFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.entities.Position
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.localDataSource.entities.Scale
import com.example.araccessories.databinding.FragmentCartBinding
import com.example.araccessories.ui.features.cartFragment.adapters.CartFragmentRecyclerView


class CartFragment : Fragment() {



    private lateinit var binding: FragmentCartBinding
    private val cartRecyclerViewAdapter = CartFragmentRecyclerView()
    private lateinit var productList: List<Products>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        initializeProductsRecyclerView()
        return binding.root
    }
    private fun initializeProductsRecyclerView(){
        initializeProducts()
        cartRecyclerViewAdapter.submitList(productList)
        binding.cartRecyclerView.adapter=cartRecyclerViewAdapter
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



}