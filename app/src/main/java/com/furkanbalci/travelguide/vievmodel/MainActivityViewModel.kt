package com.furkanbalci.travelguide.vievmodel

import android.app.Application
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.ActivityMainBinding

class MainActivityViewModel(application: Application) : BaseViewModel<Any>(application) {

    fun initializeNavigation(binding: ActivityMainBinding, navController: NavController) {

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_search -> {
                    navController.navigate(R.id.navigation_search)
                    true
                }
                R.id.navigation_trip -> {
                    navController.navigate(R.id.navigation_trip)
                    true
                }
                R.id.navigation_guide -> {
                    navController.navigate(R.id.navigation_guide)
                    true
                }
                else -> false
            }
        }

    }
}