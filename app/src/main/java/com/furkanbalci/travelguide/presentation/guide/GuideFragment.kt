package com.furkanbalci.travelguide.presentation.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentGuideBinding
import com.furkanbalci.travelguide.presentation.guide.article.ArticleAdapter
import com.furkanbalci.travelguide.presentation.guide.article.ArticleViewModel
import com.furkanbalci.travelguide.presentation.home.deals.HomeDealsAdapter
import com.furkanbalci.travelguide.presentation.search.attractions.AttractionsViewModel
import com.furkanbalci.travelguide.presentation.search.attractions.SearchAttractionsAdapter
import com.furkanbalci.travelguide.presentation.search.destination.DestinationsViewModel
import com.furkanbalci.travelguide.presentation.search.destination.SearchDestinationsAdapter


class GuideFragment : Fragment() {

    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var destinationViewModel: DestinationsViewModel
    private lateinit var binding: FragmentGuideBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guide, container, false)

        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        destinationViewModel = ViewModelProvider(this)[DestinationsViewModel::class.java]
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initializeObservers()
        this.initializeAttractions()
    }

    private fun initializeObservers() {

        //Observer deals.
        articleViewModel.success.observe(viewLifecycleOwner) {
            binding.articlesRecyclerView.adapter = ArticleAdapter(it)
        }

        //Observe loading.
        articleViewModel.loading.observe(viewLifecycleOwner) {
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
        articleViewModel.error.observe(viewLifecycleOwner) {
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
        destinationViewModel.success.observe(viewLifecycleOwner) {
            binding.countryRecyclerView.adapter = SearchDestinationsAdapter(it)
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