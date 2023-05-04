package com.example.araccessories.ui.features.mainNavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.araccessories.R
import com.example.araccessories.databinding.FragmentMainNavigationBinding
import com.example.araccessories.ui.features.botMessaging.BotFragment
import com.example.araccessories.ui.features.cartFragment.CartFragment
import com.example.araccessories.ui.features.favouriteFragment.FavouriteFragment
import com.example.araccessories.ui.features.homeFragment.HomeFragment
import com.example.araccessories.ui.features.profleFragment.ProfileFragment
import com.example.araccessories.ui.features.historyFragment.HistoryFragment


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
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.bot->{
                    replaceFragment(BotFragment())
                    true
                }

                R.id.cart->{
                    replaceFragment(CartFragment())
                    true
                }
                R.id.history->{
                    replaceFragment(HistoryFragment())
                    true
                }
                R.id.fav->{
                    replaceFragment(FavouriteFragment())
                    true
                }
                R.id.home->{
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bot->{
                    replaceFragment(BotFragment())
                    true
                }
                else -> {
                    false
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