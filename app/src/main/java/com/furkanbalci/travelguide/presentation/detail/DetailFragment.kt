package com.furkanbalci.travelguide.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.databinding.FragmentDetailBinding
import com.furkanbalci.travelguide.di.DetailObject
import com.furkanbalci.travelguide.presentation.search.SearchViewModel
import com.furkanbalci.travelguide.util.PreferencesUtil
import com.furkanbalci.travelguide.util.PreferencesUtil.get
import com.furkanbalci.travelguide.util.PreferencesUtil.set
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //Set binding.
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        //Reset selected image position.
        PreferencesUtil.defaultPrefs(requireContext())["selected-detail-image-position"] = 0

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bundle arguments.
        val detailObject = arguments?.get("detailObject") as DetailObject

        //Set binding variables.
        binding.detailObject = detailObject

        val likedList: MutableSet<String> =
            PreferencesUtil.defaultPrefs(binding.root.context).getString("liked", "")!!.split(",").toMutableSet()


        if (likedList.contains(detailObject.getCustomId())) {
            binding.likeIcon.setColorFilter(ContextCompat.getColor(view.context, R.color.selected_like_icon))
        }

        //Like button.
        binding.likeCardView.setOnClickListener {

            //If liked, remove from liked list and change icon color.
            if (likedList.contains(detailObject.getCustomId())) {
                likedList.remove(detailObject.getCustomId())
                binding.likeIcon.setColorFilter(ContextCompat.getColor(view.context, R.color.unselected_like_icon))
                Toast.makeText(binding.root.context, getString(R.string.text_remove_like), Toast.LENGTH_SHORT).show()
            } else {
                likedList.add(detailObject.getCustomId())
                binding.likeIcon.setColorFilter(ContextCompat.getColor(view.context, R.color.selected_like_icon))
                Toast.makeText(binding.root.context, getString(R.string.text_add_like), Toast.LENGTH_SHORT).show()
            }

            //Update preferences.
            PreferencesUtil.defaultPrefs(binding.root.context)["liked"] = likedList.joinToString(",")

            PreferencesUtil.defaultPrefs(binding.root.context)["liked", ""]
        }

        //If other images is more than 1, show image viewer button.
        if (detailObject.getOtherImages().size <= 1) {
            binding.recyclerViewCardView.visibility = View.GONE
        } else {
            binding.detailImageRecyclerView.adapter = DetailImageAdapter(binding, detailObject.getOtherImages())
        }

        //Just add attraction to favorites.
        if (detailObject !is Attraction) {
            binding.addBookmarkButton.visibility = View.GONE
        }

        //Full screen button click.
        binding.fullscreenIcon.setOnClickListener {
            val position = PreferencesUtil.defaultPrefs(requireContext())["selected-detail-image-position", 0]
            StfalconImageViewer.Builder(context, detailObject.getOtherImages()) { view, imageUrl ->
                Glide.with(view).load(imageUrl).centerInside().into(view)
            }.withStartPosition(position).show()
        }

        //Initialize bookmark button.
        this.initializeBookmarkButton()
    }

    private fun initializeBookmarkButton() {

        //If detail object is attraction, show bookmark button.
        if (binding.detailObject is Attraction) {
            val attraction = binding.detailObject as Attraction

            //Change button text.
            if (attraction.isBookmarked) {
                binding.addBookmarkButton.text = getString(R.string.button_remove_bookmark)
            } else {
                binding.addBookmarkButton.text = getString(R.string.button_add_bookmark)
            }

            //Button click listener.
            binding.addBookmarkButton.setOnClickListener {
                if (attraction.isBookmarked) {
                    viewModel.removeBookmark(attraction.id) {
                        Toast.makeText(binding.root.context, R.string.text_remove_bookmark, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    viewModel.addBookmark(attraction) {
                        Toast.makeText(binding.root.context, R.string.text_add_bookmark, Toast.LENGTH_SHORT).show()
                    }
                }
                attraction.isBookmarked = !attraction.isBookmarked
                //Refresh layout.
                initializeBookmarkButton()
            }
        }
    }
}