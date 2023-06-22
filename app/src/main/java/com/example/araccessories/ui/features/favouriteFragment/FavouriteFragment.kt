package com.example.araccessories.ui.features.favouriteFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.localDataSource.entities.Position
import com.example.araccessories.data.dataSource.localDataSource.entities.Products
import com.example.araccessories.data.dataSource.localDataSource.entities.Scale
import com.example.araccessories.databinding.FragmentFavouriteBinding
import com.example.araccessories.ui.features.historyFragment.adapters.HistoryRecyclerViewAdapter
import com.example.araccessories.ui.features.homeFragment.HomeFragmentViewModel
import com.example.araccessories.ui.features.signIn.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private val historyRecyclerViewAdapter = HistoryRecyclerViewAdapter()
    private lateinit var productList: List<Products>
    private val viewModel: FavouriteViewModel by viewModels()
    private val sharedViewModel: SignInViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
      //  initializeProductsRecyclerView()
        initializeProductsFav(sharedViewModel.userData.email)
        return binding.root
    }
    private fun initializeProductsRecyclerView(){
        //initializeProducts()
    //    historyRecyclerViewAdapter.submitList(productList)
        binding.cartRecyclerView.adapter=historyRecyclerViewAdapter
    }
  /*  private fun initializeProducts(){
        productList = listOf(
            Products("2","Purple Rouge",listOf(R.drawable.makeup2,R.drawable.purplerouge1,R.drawable.purplerouge2,R.drawable.purplerouge3),450.0,3.0,3,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            Products("1","Sunglasses", listOf(R.drawable.g1,R.drawable.g2),375.0,2.0,1,0,null,"The Best Sunglasses you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it",null,null),
            Products("hat.sfb","Hat",listOf(R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4),450.0,3.0,2,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            Products("2","Red Rouge",listOf(R.drawable.makeup,R.drawable.redrouge1,R.drawable.redrouge2),450.0,3.0,3,1,null,"The Best Hat you can try on , ZARA company provides you this sunglasses and gives you 14 days return back even after you try it", Scale(0.09f, 0.07f, 0.09f), Position(0.09f, 0.07f, 0.09f)),
            )
    }*/
    private fun initializeProductsFav(userEmail: String) {
        viewModel.getAllProducts(userEmail)
        viewModel.productList.observe(viewLifecycleOwner){
            historyRecyclerViewAdapter.submitList(viewModel.productList.value)
            binding.cartRecyclerView.adapter=historyRecyclerViewAdapter
        }
    }
}