package com.example.araccessories.ui.features.mainNavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentMainNavigationBinding
import com.example.araccessories.databinding.FragmentSplashBinding
import com.example.araccessories.ui.features.cartFragment.CartFragment
import com.example.araccessories.ui.features.favouriteFragment.FavouriteFragment
import com.example.araccessories.ui.features.homeFragment.HomeFragment
import com.example.araccessories.ui.features.profleFragment.ProfileFragment
import com.example.araccessories.ui.features.searchFragment.SearchFragment


class MainNavigation : Fragment() {
    private lateinit var binding: FragmentMainNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainNavigationBinding.inflate(inflater, container, false)
        replaceFragment(HomeFragment())
        navigationSelector()
        return binding.root
    }
    private fun  navigationSelector(){
        binding.bottomNavigationView.setOnClickListener {
            when(it.id){
                R.id.home->replaceFragment(HomeFragment())
                R.id.profile->replaceFragment(ProfileFragment())
                R.id.cart->replaceFragment(CartFragment())
                R.id.search->replaceFragment(SearchFragment())
                R.id.fav->replaceFragment(FavouriteFragment())

                else ->{

                }
            }

        }

    }
    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction =fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout,fragment)
        fragmentTransaction?.commit()
    }



}