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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentGuideBinding
import com.furkanbalci.travelguide.presentation.guide.article.ArticleAdapter
import com.furkanbalci.travelguide.presentation.guide.article.ArticleViewModel
import com.furkanbalci.travelguide.presentation.search.destination.DestinationsViewModel
import com.furkanbalci.travelguide.presentation.search.destination.SearchDestinationsAdapter


class GuideFragment : Fragment() {

    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var destinationViewModel: DestinationsViewModel
    private lateinit var binding: FragmentGuideBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

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
        this.initializeSearchBar()

    }

    private fun initializeSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val bundle = bundleOf("searchText" to binding.searchBar.text.toString())
                Navigation.findNavController(binding.root)
                    .navigate(R.id.searchResultFragment, bundle)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
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

            for (destination in it) {
                val tabItem = binding.tabLayout.newTab().setText(destination.name())
                binding.tabLayout.addTab(tabItem)
                //Click listener.
                tabItem.view.setOnClickListener {
                    articleViewModel.refreshData(destination.country.countryId)
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