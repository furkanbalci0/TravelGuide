package com.furkanbalci.travelguide.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.MockObject
import com.furkanbalci.travelguide.databinding.HomeDealsItemBinding
import com.furkanbalci.travelguide.di.DetailObject

class HomeDealsAdapter(private val results: List<DetailObject>) : RecyclerView.Adapter<HomeDealsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: HomeDealsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: MockObject) {

            //Set detail object to result.
            binding.detailObject = result
            binding.root.setOnClickListener {

                //Navigate to detail fragment.
                Navigation.findNavController(it)
                    .navigate(R.id.action_navigation_home_to_detailFragment, bundleOf("detailObject" to result))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeDealsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.results[position] as MockObject)
    }

    override fun getItemCount(): Int {
        return this.results.size
    }
}