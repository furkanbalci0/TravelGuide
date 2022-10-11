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
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.databinding.FragmentSearchResultBinding
import com.furkanbalci.travelguide.presentation.search.SearchViewModel
import com.furkanbalci.travelguide.presentation.search.adapter.SearchAttractionsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private var searchLiveData: MutableLiveData<String> = MutableLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //Initialize binding.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize search view.
        arguments?.let {

            //Get search query from bundle.
            val searchText = it.getString("searchText", R.string.default_attractions_city.toString())

            //Set search query to search view.
            binding.searchBar.text = Editable.Factory.getInstance().newEditable(searchText)

            //Set position of cursor to end of search query.
            binding.searchBar.setSelection(searchText.length)
        }

        //Observer search query.
        searchLiveData.observe(viewLifecycleOwner) {
            //Update attractions view model.
            searchViewModel.getAttractions(it)
        }

        //Search bar listener.
        binding.searchBar.addTextChangedListener(object : TextWatcher {

            //Delay for search query.
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

        //Initialize attractions adapter.
        this.initializeAttractions()
    }

    private fun initializeAttractions() {
        //Observer deals.

        searchViewModel.attractionsLiveData.observe(viewLifecycleOwner) {
            binding.recylcerView.adapter = SearchAttractionsAdapter(it, {
                //TODO: DELETE
            }, {
                //TODO: INSERT view model kullanarak yap.
            })

            if (it.isEmpty()) {
                binding.attractionsErrorText.visibility = View.VISIBLE
                binding.attractionsErrorText.text = getString(R.string.no_result)
            }
        }

        //Observe loading.
        searchViewModel.loading.observe(viewLifecycleOwner) {
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
        searchViewModel.error.observe(viewLifecycleOwner) {
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