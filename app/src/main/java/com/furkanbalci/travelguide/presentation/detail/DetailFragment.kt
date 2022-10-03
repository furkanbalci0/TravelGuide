package com.furkanbalci.travelguide.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkanbalci.travelguide.data.models.attractions.Attraction
import com.furkanbalci.travelguide.databinding.FragmentDetailBinding
import com.furkanbalci.travelguide.util.extensions.download


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

        val attraction = arguments?.get("attraction") as Attraction
        binding.detailObject = attraction
        binding.detailImageRecyclerView.adapter = DetailImageAdapter(attraction.getOtherImages())
    }
}