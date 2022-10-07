package com.furkanbalci.travelguide.presentation.search.attractions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.data.repositories.RoomRepository
import com.furkanbalci.travelguide.databinding.AttractionsItemBinding
import kotlinx.coroutines.runBlocking

class SearchAttractionsAdapter(private val attractionList: List<Attraction>) :
    RecyclerView.Adapter<SearchAttractionsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: AttractionsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(attraction: Attraction) {
            binding.detailObject = attraction
            binding.attractionItem.setOnClickListener {
                val bundle = bundleOf("detailObject" to attraction)
                Navigation.findNavController(it).navigate(R.id.action_navigation_search_to_detailFragment, bundle)
            }
            binding.bookmarkButton.setOnClickListener {
                attraction.isBookmarked = !attraction.isBookmarked
                runBlocking {
                    if (attraction.isBookmarked) {
                        RoomRepository(binding.root.context).delete(attraction)
                        Toast.makeText(binding.root.context, "BİR ŞEY silindi", Toast.LENGTH_SHORT).show()
                    } else {
                        RoomRepository(binding.root.context).insert(attraction)
                        Toast.makeText(binding.root.context, "BİR ŞEY EKLENDİ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (attraction.isBookmarked) {
                binding.bookmarkImage.setImageResource(R.drawable.ic_filled_bookmark)
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