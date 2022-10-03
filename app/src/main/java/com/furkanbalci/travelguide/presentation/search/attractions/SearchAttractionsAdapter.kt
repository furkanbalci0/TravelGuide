package com.furkanbalci.travelguide.presentation.search.attractions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.databinding.AttractionsItemBinding

class SearchAttractionsAdapter(private val attractionList: List<Attraction>) :
    RecyclerView.Adapter<SearchAttractionsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: AttractionsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(attraction: Attraction) {
            binding.attraction = attraction
            binding.root.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_navigation_search_to_detailFragment, bundleOf("attraction" to attraction))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AttractionsItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(attractionList[position])
    }

    override fun getItemCount(): Int {
        return this.attractionList.size
    }
}