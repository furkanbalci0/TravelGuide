package com.furkanbalci.travelguide.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.databinding.AttractionsItemBinding
import com.furkanbalci.travelguide.di.DetailObject

class SearchAttractionsAdapter(
    private val attractionList: List<DetailObject>,
    var onBookmarkDeleteButtonClick: (String) -> Unit,
    var onBookmarkInsertButtonClick: (Attraction) -> Unit
) :
    RecyclerView.Adapter<SearchAttractionsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: AttractionsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(attraction: Attraction) {

            //Set detail object to attraction.
            binding.detailObject = attraction
            binding.attractionItem.setOnClickListener {

                //Navigate to detail fragment.
                val bundle = bundleOf("detailObject" to attraction)
                Navigation.findNavController(it).navigate(R.id.detailFragment, bundle)
            }

            //Set bookmark click listener.
            binding.bookmarkButton.setOnClickListener {
                if (attraction.isBookmarked) {
                    onBookmarkDeleteButtonClick(attraction.id)
                    Toast.makeText(binding.root.context, R.string.text_remove_bookmark, Toast.LENGTH_SHORT).show()
                } else {
                    onBookmarkInsertButtonClick(attraction)
                    Toast.makeText(binding.root.context, R.string.text_add_bookmark, Toast.LENGTH_SHORT).show()
                }
            }

            //If attraction is bookmarked, set bookmark icon color to selected color.
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
        holder.bind(attractionList[position] as Attraction)
    }

    override fun getItemCount(): Int {
        return this.attractionList.size
    }
}