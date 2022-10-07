package com.furkanbalci.travelguide.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.databinding.FragmentDetailBinding
import com.furkanbalci.travelguide.di.DetailObject
import com.furkanbalci.travelguide.util.PreferencesUtil
import com.furkanbalci.travelguide.util.PreferencesUtil.get
import com.furkanbalci.travelguide.util.PreferencesUtil.set
import com.stfalcon.imageviewer.StfalconImageViewer


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

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
    }
}