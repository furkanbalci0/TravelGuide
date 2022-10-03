package com.furkanbalci.travelguide.presentation.search.destination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.data.models.Destination
import com.furkanbalci.travelguide.databinding.SearchDestinationItemBinding

class SearchDestinationsAdapter(private val destinationList: List<Destination>) :
    RecyclerView.Adapter<SearchDestinationsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SearchDestinationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(destination: Destination) {
            binding.destination = destination
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