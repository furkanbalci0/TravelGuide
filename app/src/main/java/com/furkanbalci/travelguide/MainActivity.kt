package com.furkanbalci.travelguide

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.furkanbalci.travelguide.databinding.ActivityMainBinding
import com.furkanbalci.travelguide.vievmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)

        setContentView(binding.root)

        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

    }

    override fun onStart() {
        super.onStart()
        mainActivityViewModel.initializeNavigation(binding,
            findNavController(R.id.nav_host_fragment_container))

    }
}