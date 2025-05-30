package kz.grand_hotel.ui.menu.ui.home.Hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentBottomSheetHotelBinding

class HotelBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetHotelBinding? = null
    private val binding get() = _binding!!

    override fun getTheme(): Int = R.style.RoundedBottomSheetDialogTheme

    companion object {
        private const val ARG_HOTEL = "arg_hotel"

        fun newInstance(hotel: Hotels): HotelBottomSheetFragment {
            val json = Gson().toJson(hotel)
            return HotelBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_HOTEL, json)
                }
            }
        }
    }

    private lateinit var hotel: Hotels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ARG_HOTEL)?.let { json ->
            hotel = Gson().fromJson(json, Hotels::class.java)
        } ?: run {
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.hotelNameTextView.text     = hotel.name
        binding.hotelLocationTextView.text = ""
        binding.hotelPriceTextView.text    = hotel.price
        binding.hotelRatingTextView.text   = hotel.rating

        Glide.with(this)
            .load(hotel.image)
            .centerCrop()
            .into(binding.hotelImageView)

        binding.bookingButton.setOnClickListener {

            val bundle = Bundle().apply {
                putInt   ("id", hotel.id)
                putInt   ("imageResId", hotel.image)
                putString("name",       hotel.name)
                putString("location",   hotel.location)
                putDouble("latitude",   hotel.locationLatLng.latitude)
                putDouble("longitude",  hotel.locationLatLng.longitude)
                putString("price",      hotel.price)
                putString("rating",     hotel.rating)
            }
            findNavController().navigate(
                R.id.action_mapFragment_to_hotelDetailsFragment,
                bundle
            )
            dismiss()
        }

        binding.chatButton.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
