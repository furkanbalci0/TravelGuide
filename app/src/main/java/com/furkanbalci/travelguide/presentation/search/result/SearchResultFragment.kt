package com.furkanbalci.travelguide.presentation.search.result

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentSearchResultBinding
import com.furkanbalci.travelguide.presentation.search.attractions.AttractionsViewModel
import com.furkanbalci.travelguide.presentation.search.attractions.SearchAttractionsAdapter


class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var attractionsViewModel: AttractionsViewModel
    private var searchLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        attractionsViewModel = ViewModelProvider(this)[AttractionsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val searchText = it.getString("searchText", "Amsterdam")
            binding.searchBar.text = Editable.Factory.getInstance().newEditable(searchText)
            binding.searchBar.setSelection(searchText.length)
        }

        searchLiveData.observe(viewLifecycleOwner) {
            attractionsViewModel.getData(it)
        }


        binding.searchBar.addTextChangedListener(object : TextWatcher {

            var handler: Handler = Handler(Looper.getMainLooper() /*UI thread*/)
            var workRunnable: Runnable? = null
            override fun afterTextChanged(s: Editable) {
                workRunnable?.let { handler.removeCallbacks(it) }
                if (s.toString().isEmpty()) {
                    workRunnable?.let { handler.removeCallbacks(it) }
                    return
                }
                workRunnable = Runnable { searchLiveData.postValue(s.toString().trim().replace(" ", "_")) }
                handler.postDelayed(workRunnable!!, 1000 /*delay*/)

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        this.initializeAttractions()
    }

    private fun initializeAttractions() {
        //Observer deals.


        attractionsViewModel.success.observe(viewLifecycleOwner) {
            binding.recylcerView.adapter = SearchAttractionsAdapter(it)
            if (it.isEmpty()) {
                binding.attractionsErrorText.visibility = View.VISIBLE
                binding.attractionsErrorText.text = "No results found."
            }
        }


        //Observe loading.
        attractionsViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.recylcerView.visibility = View.GONE
                binding.attractionsErrorText.visibility = View.GONE
                binding.attractionsProgressBar.visibility = View.VISIBLE
            } else {
                binding.recylcerView.visibility = View.VISIBLE
                binding.attractionsErrorText.visibility = View.GONE
                binding.attractionsProgressBar.visibility = View.GONE
            }
        }

        //Observe error.
        attractionsViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.recylcerView.visibility = View.GONE
                binding.attractionsErrorText.visibility = View.VISIBLE
            } else {
                binding.recylcerView.visibility = View.VISIBLE
                binding.attractionsErrorText.visibility = View.GONE
            }
        }
    }


}