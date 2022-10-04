package com.furkanbalci.travelguide.presentation.search.destination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.Destination
import com.furkanbalci.travelguide.databinding.SearchDestinationItemBinding

class SearchDestinationsAdapter(private val destinationList: List<Destination>) :
    RecyclerView.Adapter<SearchDestinationsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SearchDestinationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(destination: Destination) {
            binding.destination = destination
            binding.root.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_navigation_search_to_detailFragment, bundleOf("detailObject" to destination))
                val preferences = binding.root.context.getSharedPreferences("com.furkanbalci.travelguide", 0)
                preferences.edit().putString("last-selected-country", destination.country.countryId).apply()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchDestinationItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(destinationList[position])
    }

    override fun getItemCount(): Int {
        return this.destinationList.size
    }
}