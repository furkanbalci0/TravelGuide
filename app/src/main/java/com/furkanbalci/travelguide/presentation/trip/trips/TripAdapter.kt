package com.furkanbalci.travelguide.presentation.trip.trips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.databinding.TripItemBinding
import com.furkanbalci.travelguide.di.DetailObject

class TripAdapter(private val trips: List<DetailObject>) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: TripItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: DetailObject) {
            binding.detailObject = result
            binding.root.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_navigation_trip_to_detailFragment, bundleOf("detailObject" to result))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TripItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.trips[position])
    }

    override fun getItemCount(): Int {
        return this.trips.size
    }
}