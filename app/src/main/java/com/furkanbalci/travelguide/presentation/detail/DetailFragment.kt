package com.furkanbalci.travelguide.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.furkanbalci.travelguide.databinding.FragmentDetailBinding
import com.furkanbalci.travelguide.di.DetailObject
import com.stfalcon.imageviewer.StfalconImageViewer


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailObject = arguments?.get("detailObject") as DetailObject
        binding.detailObject = detailObject
        if (detailObject.getOtherImages().size == 1) {
            binding.recyclerViewCardView.visibility = View.GONE
        } else {
            binding.detailImageRecyclerView.adapter = DetailImageAdapter(binding, detailObject.getOtherImages())
        }

        binding.fullscreenIcon.setOnClickListener {
            val preferences = requireContext().getSharedPreferences("com.furkanbalci.travelguide", 0)
            StfalconImageViewer.Builder(context, detailObject.getOtherImages()) { view, imageUrl ->
                Glide.with(view).load(imageUrl).centerInside().into(view)
            }.withStartPosition(preferences.getInt("selected-detail-image-position", 0)).show()
        }
    }
}