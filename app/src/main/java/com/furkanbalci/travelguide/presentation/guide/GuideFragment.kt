package com.furkanbalci.travelguide.presentation.guide

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
import com.furkanbalci.travelguide.databinding.FragmentGuideBinding
import com.furkanbalci.travelguide.presentation.search.DestinationsViewModel
import com.furkanbalci.travelguide.presentation.search.adapter.DestinationsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GuideFragment : Fragment() {

    private val guideViewModel: GuideViewModel by viewModels()
    private val destinationViewModel: DestinationsViewModel by viewModels()

    private lateinit var binding: FragmentGuideBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guide, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize articles.
        initializeArticles()

        //Initialize attractions.
        initializeAttractions()

        //Initialize search bar.
        initializeSearchBar()

    }

    private fun initializeSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                //Navigate to search fragment.
                val bundle = bundleOf("searchText" to binding.searchBar.text.toString())
                Navigation.findNavController(binding.root)
                    .navigate(R.id.searchResultFragment, bundle)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initializeArticles() {

        //Observer deals.
        guideViewModel.articlesLiveData.observe(viewLifecycleOwner) {
            binding.articlesRecyclerView.adapter = ArticleAdapter(it)
        }

        //Observe loading.
        guideViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.articlesRecyclerView.visibility = View.GONE
                binding.articlesErrorText.visibility = View.GONE
                binding.articlesProgressBar.visibility = View.VISIBLE
            } else {
                binding.articlesRecyclerView.visibility = View.VISIBLE
                binding.articlesErrorText.visibility = View.GONE
                binding.articlesProgressBar.visibility = View.GONE
            }
        }

        //Observe error.
        guideViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.articlesRecyclerView.visibility = View.GONE
                binding.articlesErrorText.visibility = View.VISIBLE
            } else {
                binding.articlesRecyclerView.visibility = View.VISIBLE
                binding.articlesErrorText.visibility = View.GONE
            }
        }
    }

    private fun initializeAttractions() {
        //Observer deals.
        destinationViewModel.destinationLiveData.observe(viewLifecycleOwner) {
            binding.countryRecyclerView.adapter = DestinationsAdapter(it)

            for (destination in it) {
                val tabItem = binding.tabLayout.newTab().setText(destination.customName())
                binding.tabLayout.addTab(tabItem)
                //Click listener.
                tabItem.view.setOnClickListener {
                    guideViewModel.refreshData(destination.country.countryId)
                }

            }
        }


        //Observe loading.
        destinationViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.countryRecyclerView.visibility = View.GONE
                binding.countryErrorText.visibility = View.GONE
                binding.countryProgressBar.visibility = View.VISIBLE
            } else {
                binding.countryRecyclerView.visibility = View.VISIBLE
                binding.countryErrorText.visibility = View.GONE
                binding.countryProgressBar.visibility = View.GONE
            }
        }

        //Observe error.
        destinationViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.countryRecyclerView.visibility = View.GONE
                binding.countryErrorText.visibility = View.VISIBLE
            } else {
                binding.countryRecyclerView.visibility = View.VISIBLE
                binding.countryErrorText.visibility = View.GONE
            }
        }
    }

}