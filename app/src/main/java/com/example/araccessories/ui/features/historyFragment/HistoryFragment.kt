package com.example.araccessories.ui.features.historyFragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Position
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Products
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Scale
import com.example.araccessories.databinding.FragmentHistoryBinding
import com.example.araccessories.databinding.FragmentSplashBinding
import com.example.araccessories.ui.features.cartFragment.adapters.CartFragmentRecyclerView
import com.example.araccessories.ui.features.historyFragment.adapters.HistoryRecyclerViewAdapter


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val historyRecyclerViewAdapter = HistoryRecyclerViewAdapter()
    private lateinit var productList: List<Products>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        initializeProductsRecyclerView()
        return binding.root
    }
    private fun initializeProductsRecyclerView(){
        initializeProducts()
        historyRecyclerViewAdapter.submitList(productList)
        binding.cartRecyclerView.adapter=historyRecyclerViewAdapter
    }
    private fun initializeProducts(){
        productList = listOf(
            Products("mask.sfb","Mask",listOf(R.drawable.mask1,R.drawable.mask2,R.drawable.mask3),450.0,3.0,4,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(2f, 2f, 2f), Position(0f, -0.005f, 0.017f)),
            Products("clownMask.sfb","Clown Mask",listOf(R.drawable.cm1,R.drawable.cm2,R.drawable.cm3),450.0,3.0,4,0,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(6f, 6f, 6f), Position(0f, -0.005f, 0.017f)),
            Products("maskamirat.sfb","Queen Mask",listOf(R.drawable.queen1,R.drawable.queen2,R.drawable.queen3),450.0,3.0,4,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(7f, 7f, 7f), Position(0f, +0.075f, 0.012f)),
            Products("2","Fox Make Up",listOf(R.drawable.fox_face_mesh_texture,R.drawable.fox_face_mesh_texture,R.drawable.fox_face_mesh_texture),450.0,3.0,3,0,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),

            )
    }

}