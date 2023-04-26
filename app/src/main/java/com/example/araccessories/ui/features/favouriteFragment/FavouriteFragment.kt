package com.example.araccessories.ui.features.favouriteFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Position
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Products
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Scale
import com.example.araccessories.databinding.FragmentFavouriteBinding
import com.example.araccessories.databinding.FragmentHistoryBinding
import com.example.araccessories.ui.features.historyFragment.adapters.HistoryRecyclerViewAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val historyRecyclerViewAdapter = HistoryRecyclerViewAdapter()
    private lateinit var productList: List<Products>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
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
            Products("2","Purple Rouge",listOf(R.drawable.makeup2,R.drawable.purplerouge1,R.drawable.purplerouge2,R.drawable.purplerouge3),450.0,3.0,3,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            Products("1","Sunglasses", listOf(R.drawable.g1,R.drawable.g2),375.0,2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",null,null),
            Products("hat.sfb","Hat",listOf(R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4),450.0,3.0,2,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            Products("2","Red Rouge",listOf(R.drawable.makeup,R.drawable.redrouge1,R.drawable.redrouge2),450.0,3.0,3,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            )
    }
}