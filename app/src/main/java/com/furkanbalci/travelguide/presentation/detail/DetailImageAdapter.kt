package com.furkanbalci.travelguide.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.databinding.AttractionsItemBinding
import com.furkanbalci.travelguide.databinding.DetailImageItemBinding
import com.furkanbalci.travelguide.util.extensions.download

class DetailImageAdapter(private val imageList: List<String>) :
    RecyclerView.Adapter<DetailImageAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: DetailImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.ivDetailImage.download(imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DetailImageItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return this.imageList.size
    }
}