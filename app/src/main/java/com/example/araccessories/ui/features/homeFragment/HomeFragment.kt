package com.example.araccessories.ui.features.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.araccessories.R
import com.example.araccessories.data.dataSource.remoteDataSource.entities.Ad
import com.example.araccessories.databinding.FragmentHomeBinding
import com.example.araccessories.ui.features.homeFragment.adapters.AdsRecyclerViewAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*
import kotlin.concurrent.schedule


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adList: List<Ad>
    private val adsRecyclerViewAdapter = AdsRecyclerViewAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initializeRecyclerView()
        return binding.root
    }
    private fun initializeRecyclerView(){
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
    private fun setAutoScroll(){

    }


}