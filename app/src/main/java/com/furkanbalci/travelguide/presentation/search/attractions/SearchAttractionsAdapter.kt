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

    private lateinit var roomRepository: RoomRepository
    inner class ViewHolder(private val binding: AttractionsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            roomRepository = RoomRepository(binding.root.context)
        }
        fun bind(attraction: Attraction) {
            binding.detailObject = attraction
            binding.attractionItem.setOnClickListener {
                val bundle = bundleOf("detailObject" to attraction)
                Navigation.findNavController(it).navigate(R.id.detailFragment, bundle)
            }
            println("Test 1")
            binding.bookmarkButton.setOnClickListener {
                println("Test 2")
                runBlocking {
                    println("Test 3")
                    val bookmarks = roomRepository.getBookmarks()
                    val test = bookmarks.asSequence().map { it.attractionId }.toList()
                    if (test.contains(attraction.id)) {
                        roomRepository.delete(attraction.id)
                        Toast.makeText(binding.root.context, "Removed from bookmarks", Toast.LENGTH_SHORT).show()
                    } else {
                        roomRepository.insert(attraction)
                        Toast.makeText(binding.root.context, "Added to bookmarks", Toast.LENGTH_SHORT).show()
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