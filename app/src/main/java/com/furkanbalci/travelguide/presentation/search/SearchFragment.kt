package com.furkanbalci.travelguide.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentSearchBinding
import com.furkanbalci.travelguide.presentation.search.adapter.DestinationsAdapter
import com.furkanbalci.travelguide.presentation.search.adapter.SearchAttractionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    //View models.
    private val searchViewModel: SearchViewModel by viewModels()
    private val destinationViewModel: DestinationsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize destinations
        initializeDestinations()

        //Initialize attractions
        initializeAttractions()

        //Initialize search bar
        initializeSearchBar()
    }

    private fun initializeSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                //Navigate to search result fragment.
                val bundle = bundleOf("searchText" to binding.searchBar.text.toString())
                Navigation.findNavController(binding.root)
                    .navigate(R.id.searchResultFragment, bundle)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initializeDestinations() {
        //Observer deals.
        destinationViewModel.destinationLiveData.observe(viewLifecycleOwner) {
            binding.searchDestinationsRecyclerView.adapter = DestinationsAdapter(it)
        }

        //Observe loading.
        destinationViewModel.loading.observe(viewLifecycleOwner) {
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
        destinationViewModel.error.observe(viewLifecycleOwner) {
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
        //Observer data.
        searchViewModel.attractionsLiveData.observe(viewLifecycleOwner) {
            binding.searchNearbyAttractionsRecyclerView.adapter = SearchAttractionsAdapter(it, { attractionId ->
                searchViewModel.removeBookmark(attractionId) {
                    searchViewModel.getAttractions(searchViewModel.lastSelectedCity)
                }
            }, { attraction ->
                searchViewModel.addBookmark(attraction) {
                    searchViewModel.getAttractions(searchViewModel.lastSelectedCity)
                }
            })
        }


        //Observe loading.
        searchViewModel.loading.observe(viewLifecycleOwner) {
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
        searchViewModel.error.observe(viewLifecycleOwner) {
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