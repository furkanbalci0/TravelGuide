package com.furkanbalci.travelguide.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //Set binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        //Observe deals.
        initializeDeals()

        //Initialize tab layout.
        initializeTabLayout()

        //Initialize buttons.
        initializeButtons()

        return binding.root
    }

    /**
     * Static buttons.
     */
    private fun initializeButtons() {

        //Destination buttons.
        binding.homeFlightsLayout.setOnClickListener {
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
            //Click sound.
            binding.tabLayout.playSoundEffect(android.view.SoundEffectConstants.CLICK)
        }
        binding.homeHotelsLayout.setOnClickListener {
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))
            //Click sound.
            binding.tabLayout.playSoundEffect(android.view.SoundEffectConstants.CLICK)
        }
        binding.homeTaxiLayout.setOnClickListener {
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(3))
            //Click sound.
            binding.tabLayout.playSoundEffect(android.view.SoundEffectConstants.CLICK)
        }
        binding.homeCarsLayout.setOnClickListener {
            Snackbar.make(binding.root, R.string.maintenance_text, Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).show()
        }

    }

    private fun initializeTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                //Tab layout click listener.
                when (tab.position) {
                    0 -> homeViewModel.refreshData("flight|hotel|transportation")
                    1 -> homeViewModel.refreshData("flight")
                    2 -> homeViewModel.refreshData("hotel")
                    3 -> homeViewModel.refreshData("transportation")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initializeDeals() {

        //Observer deals.
        homeViewModel.attractionsLiveData.observe(viewLifecycleOwner) {
            binding.dealsRecyclerview.adapter = HomeDealsAdapter(it)
        }

        //Observe loading.
        homeViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.dealsRecyclerview.visibility = View.GONE
                binding.dealsErrorText.visibility = View.GONE
                binding.dealsProgressBar.visibility = View.VISIBLE
            } else {
                binding.dealsRecyclerview.visibility = View.VISIBLE
                binding.dealsErrorText.visibility = View.GONE
                binding.dealsProgressBar.visibility = View.GONE
            }
        }

        //Observe error.
        homeViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.dealsRecyclerview.visibility = View.GONE
                binding.dealsErrorText.visibility = View.VISIBLE
            } else {
                binding.dealsRecyclerview.visibility = View.VISIBLE
                binding.dealsErrorText.visibility = View.GONE
            }
        }


    }
}