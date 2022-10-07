package com.furkanbalci.travelguide.presentation.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.databinding.FragmentTripBinding
import com.furkanbalci.travelguide.presentation.search.attractions.SearchAttractionsAdapter
import com.furkanbalci.travelguide.presentation.trip.bookmark.BookmarkViewModel
import com.furkanbalci.travelguide.presentation.trip.trips.TripAdapter
import com.google.android.material.tabs.TabLayout


class TripFragment : Fragment() {

    private lateinit var binding: FragmentTripBinding
    private lateinit var bookmarkViewModel: BookmarkViewModel
    private var bookmarkList = mutableListOf<Attraction>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip, container, false)

        bookmarkViewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> {
                        binding.recyclerView.adapter = SearchAttractionsAdapter(bookmarkList)
                        binding.floatingActionButton.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.recyclerView.adapter = TripAdapter(bookmarkList)
                        binding.floatingActionButton.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        //binding.recyclerView.adapter = BookmarkAdapter(bookmarkViewModel.getData())
        bookmarkViewModel.success.observe(viewLifecycleOwner) {
            bookmarkList.clear()
            bookmarkList.addAll(it)
            binding.recyclerView.adapter = SearchAttractionsAdapter(it)
        }
    }
}