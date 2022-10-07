package com.furkanbalci.travelguide.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentSearchBinding
import com.furkanbalci.travelguide.presentation.search.attractions.AttractionsViewModel
import com.furkanbalci.travelguide.presentation.search.attractions.SearchAttractionsAdapter
import com.furkanbalci.travelguide.presentation.search.destination.DestinationsViewModel
import com.furkanbalci.travelguide.presentation.search.destination.SearchDestinationsAdapter

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var destinationsViewModel: DestinationsViewModel
    private lateinit var attractionsViewModel: AttractionsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        destinationsViewModel = ViewModelProvider(this)[DestinationsViewModel::class.java]
        attractionsViewModel = ViewModelProvider(this)[AttractionsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize destinations
        this.initializeDestinations()

        //Initialize attractions
        this.initializeAttractions()
    }

    private fun initializeDestinations() {
        //Observer deals.
        destinationsViewModel.success.observe(viewLifecycleOwner) {
            binding.searchDestinationsRecyclerView.adapter = SearchDestinationsAdapter(it)
        }

        //Observe loading.
        destinationsViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchDestinationsRecyclerView.visibility = View.GONE
                binding.destinationsErrorText.visibility = View.GONE
                binding.destinationsProgressBar.visibility = View.VISIBLE
            } else {
                binding.searchDestinationsRecyclerView.visibility = View.VISIBLE
                binding.destinationsErrorText.visibility = View.GONE
                binding.destinationsProgressBar.visibility = View.GONE
            }
        }

        //Observe error.
        destinationsViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchDestinationsRecyclerView.visibility = View.GONE
                binding.destinationsErrorText.visibility = View.VISIBLE
            } else {
                binding.searchDestinationsRecyclerView.visibility = View.VISIBLE
                binding.destinationsErrorText.visibility = View.GONE
            }
        }
    }

    private fun initializeAttractions() {
        //Observer deals.


        attractionsViewModel.success.observe(viewLifecycleOwner) {
            binding.searchNearbyAttractionsRecyclerView.adapter = SearchAttractionsAdapter(it)
        }


        //Observe loading.
        attractionsViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchNearbyAttractionsRecyclerView.visibility = View.GONE
                binding.attractionsErrorText.visibility = View.GONE
                binding.attractionsProgressBar.visibility = View.VISIBLE
            } else {
                binding.searchNearbyAttractionsRecyclerView.visibility = View.VISIBLE
                binding.attractionsErrorText.visibility = View.GONE
                binding.attractionsProgressBar.visibility = View.GONE
            }
        }

        //Observe error.
        attractionsViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchNearbyAttractionsRecyclerView.visibility = View.GONE
                binding.attractionsErrorText.visibility = View.VISIBLE
            } else {
                binding.searchNearbyAttractionsRecyclerView.visibility = View.VISIBLE
                binding.attractionsErrorText.visibility = View.GONE
            }
        }
    }
}