package com.furkanbalci.travelguide.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentHomeBinding
import com.furkanbalci.travelguide.presentation.home.deals.HomeDealsAdapter
import com.furkanbalci.travelguide.presentation.home.deals.HomeDealsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dealsViewModel: HomeDealsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //Set view model.
        dealsViewModel = ViewModelProvider(this)[HomeDealsViewModel::class.java]

        //Set binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        //Observe deals.
        this.initializeObservers()

        //Initialize tab layout.
        this.initializeTabLayout()

        //Initialize buttons.
        this.initializeButtons()

        return binding.root
    }

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
            Snackbar.make(binding.root, "In care...", Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).show()
        }

    }

    private fun initializeTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> dealsViewModel.refreshData(null)
                    1 -> dealsViewModel.refreshData("flight")
                    2 -> dealsViewModel.refreshData("hotel")
                    3 -> dealsViewModel.refreshData("transportation")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun initializeObservers() {

        //Observer deals.
        dealsViewModel.success.observe(viewLifecycleOwner) {
            binding.dealsRecyclerview.adapter = HomeDealsAdapter(it)
        }

        //Observe loading.
        dealsViewModel.loading.observe(viewLifecycleOwner) {
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
        dealsViewModel.error.observe(viewLifecycleOwner) {
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