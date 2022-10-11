package com.furkanbalci.travelguide.presentation.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.furkanbalci.travelguide.R
import com.furkanbalci.travelguide.data.models.Trip
import com.furkanbalci.travelguide.databinding.TripBottomSheetsBinding
import com.furkanbalci.travelguide.presentation.detail.DetailViewModel
import com.furkanbalci.travelguide.presentation.search.DestinationsViewModel
import com.furkanbalci.travelguide.presentation.trip.adapter.TripBottomSheetAdapter
import com.furkanbalci.travelguide.util.DateUtil
import com.furkanbalci.travelguide.util.PreferencesUtil
import com.furkanbalci.travelguide.util.PreferencesUtil.set
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class TripBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: TripBottomSheetsBinding
    private val viewModel: DetailViewModel by viewModels()
    private val destinationsViewModel: DestinationsViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = TripBottomSheetsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tripStartDateEditText.setOnClickListener {
            DateUtil.showDatePickerDialog(it as EditText, false)
        }
        binding.tripEndDateEditText.setOnClickListener {
            DateUtil.showDatePickerDialog(it as EditText, true)
        }

        //Reset variables.
        PreferencesUtil.defaultPrefs(view.context).let { _preferences ->
            _preferences["trip-destination-id"] = null
            _preferences["trip-destination-name"] = null
            _preferences["trip-destination-image"] = null
            _preferences["trip-destination-ending-time"] = null
            _preferences["trip-destination-starting-time"] = null
        }

        this.initializeDestinations()

        //Save trip button click listener.
        this.initializeButtons()
    }


    private fun initializeDestinations() {
        //Observer deals.
        destinationsViewModel.destinationLiveData.observe(viewLifecycleOwner) {
            binding.searchDestinationsRecyclerView.adapter = TripBottomSheetAdapter(it)
        }

        //Observe loading.
        destinationsViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchDestinationsRecyclerView.visibility = View.GONE
                binding.destinationsErrorText.visibility = View.GONE
                binding.destinationsProgressBar.visibility = View.VISIBLE
            } else {
                binding.searchDestinationsRecyclerView.visibility = View.VISIBLE
                binding.destinationsErrorText.visibility = View.GONE
                binding.destinationsProgressBar.visibility = View.GONE
            }
        }

        //Observe error.
        destinationsViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchDestinationsRecyclerView.visibility = View.GONE
                binding.destinationsErrorText.visibility = View.VISIBLE
            } else {
                binding.searchDestinationsRecyclerView.visibility = View.VISIBLE
                binding.destinationsErrorText.visibility = View.GONE
            }
        }
    }

    //Initialize buttons.
    private fun initializeButtons() {

        binding.addTripButton.setOnClickListener {
            val destinationName = PreferencesUtil.defaultPrefs(it.context).getString("trip-destination-name", null)
            val destinationImage = PreferencesUtil.defaultPrefs(it.context).getString("trip-destination-image", null)
            val startingDate: Long = PreferencesUtil.defaultPrefs(it.context).getLong("trip-destination-starting-time", 0L)
            val endingDate: Long = PreferencesUtil.defaultPrefs(it.context).getLong("trip-destination-ending-time", 0L)

            println("OLLYY: $destinationName $destinationImage $startingDate $endingDate")
            if (destinationName != null && destinationImage != null && startingDate != 0L && endingDate != 0L) {

                val trip = Trip(
                    id = UUID.randomUUID().toString(),
                    tripName = destinationName,
                    mainImage = destinationImage,
                    startingDate = startingDate,
                    endingDate = endingDate,
                )

                //todo: Add trip to database.

                //Close bottom sheet.
                this.dismiss()
            } else {
                Toast.makeText(it.context, getString(R.string.please_all_fill_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }
}