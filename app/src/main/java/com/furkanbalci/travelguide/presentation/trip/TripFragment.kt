package com.furkanbalci.travelguide.presentation.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.databinding.FragmentTripBinding
import com.furkanbalci.travelguide.presentation.trip.bookmark.BookmarkAdapter
import com.furkanbalci.travelguide.presentation.trip.trips.TripAdapter
import com.google.android.material.tabs.TabLayout
import java.util.*


class TripFragment : Fragment() {

    private lateinit var binding: FragmentTripBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> {
                        binding.recyclerView.adapter = TripAdapter(
                            listOf(
                                Trip("a", Date(), Date(), listOf(""), ""),
                                Trip("b", Date(), Date(), listOf(""), ""),
                                Trip("c", Date(), Date(), listOf(""), ""),
                                Trip("d", Date(), Date(), listOf(""), ""),
                                Trip("e", Date(), Date(), listOf(""), ""),
                            )
                        )
                        binding.floatingActionButton.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.recyclerView.adapter = BookmarkAdapter(
                            listOf(
                                Trip("aaaaaa", Date(), Date(), listOf(""), ""),
                                Trip("baaa", Date(), Date(), listOf(""), ""),
                                Trip("caa", Date(), Date(), listOf(""), ""),
                                Trip("daaa", Date(), Date(), listOf(""), ""),
                                Trip("eaaaaaa", Date(), Date(), listOf(""), ""),
                            )
                        )
                        binding.floatingActionButton.visibility = View.GONE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.recyclerView.adapter = TripAdapter(
            listOf(
                Trip("a", Date(), Date(), listOf(""), ""),
                Trip("b", Date(), Date(), listOf(""), ""),
                Trip("c", Date(), Date(), listOf(""), ""),
                Trip("d", Date(), Date(), listOf(""), ""),
                Trip("e", Date(), Date(), listOf(""), ""),
            )
        )
    }
}