package com.furkanbalci.travelguide.presentation.trip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.data.models.Destination
import com.furkanbalci.travelguide.databinding.SearchDestinationItemBinding
import com.furkanbalci.travelguide.util.PreferencesUtil
import com.furkanbalci.travelguide.util.PreferencesUtil.set

class TripBottomSheetAdapter(private val destinationList: List<Destination>) :
    RecyclerView.Adapter<TripBottomSheetAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SearchDestinationItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(destination: Destination) {
            binding.destination = destination
            binding.root.setOnClickListener {
                PreferencesUtil.defaultPrefs(it.context).let { _preferences ->
                    _preferences["trip-destination-id"] = destination.getCustomId()
                    _preferences["trip-destination-name"] = destination.customName()
                    _preferences["trip-destination-image"] = destination.mainImageUrl()
                }

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