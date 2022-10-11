package com.furkanbalci.travelguide.presentation.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentTripBinding
import com.furkanbalci.travelguide.di.DetailObject
import com.furkanbalci.travelguide.presentation.search.SearchViewModel
import com.furkanbalci.travelguide.presentation.search.adapter.SearchAttractionsAdapter
import com.furkanbalci.travelguide.presentation.trip.adapter.TripAdapter
import com.furkanbalci.travelguide.util.Constants
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripFragment : Fragment() {

    private lateinit var binding: FragmentTripBinding
    private lateinit var tripAdapter: TripAdapter
    private val bottomSheet = TripBottomSheetFragment() {
        tripViewModel.addTrip(it)
    }
    private val searchViewModel: SearchViewModel by viewModels()
    private val tripViewModel: TripViewModel by viewModels()

    private var bookmarkList = mutableListOf<DetailObject>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //Initialize binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tripAdapter = TripAdapter { tripId ->
            tripViewModel.deleteTrip(tripId)
        }

        //Tab layout click listener.
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> {

                        binding.recyclerView.adapter = tripAdapter
                        binding.floatingActionButton.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.recyclerView.adapter = SearchAttractionsAdapter(bookmarkList, { attractionId ->
                            searchViewModel.removeBookmark(attractionId) { searchViewModel.findAttractions() }
                        }, { attraction ->
                            searchViewModel.addBookmark(attraction) { searchViewModel.findAttractions() }
                        })
                        binding.floatingActionButton.visibility = View.GONE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        searchViewModel.bookmarkLiveData.observe(viewLifecycleOwner) {

            //Update bookmark list.
            bookmarkList.clear()
            bookmarkList.addAll(it)

            if (binding.tabLayout.selectedTabPosition == 1) {
                binding.recyclerView.adapter = SearchAttractionsAdapter(it, { attractionId ->
                    searchViewModel.removeBookmark(attractionId) { searchViewModel.findAttractions() }
                }, { attraction ->
                    searchViewModel.addBookmark(attraction) { searchViewModel.findAttractions() }
                })
            }
        }

        tripViewModel.tripLiveData.observe(viewLifecycleOwner) {
            if (binding.tabLayout.selectedTabPosition == 0) {
                binding.recyclerView.adapter = tripAdapter
                tripAdapter.setData(it)
            }
        }

        tripViewModel.listUpdateLiveData.observe(viewLifecycleOwner) {
            println("TESTT: 1")
            tripViewModel.getTrips()
            bottomSheet.dismiss()
            println("TESTT: 2")
        }

        tripViewModel.listRemoveLiveData.observe(viewLifecycleOwner) {
            tripViewModel.getTrips()
        }

        binding.floatingActionButton.setOnClickListener {
            bottomSheet.show(childFragmentManager, Constants.TAG)
        }

    }
}