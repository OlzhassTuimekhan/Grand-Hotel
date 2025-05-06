package kz.grand_hotel.ui.menu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.grand_hotel.R

class HotelBottomSheetFragment(private val hotel: HotelsInMap) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_bottom_sheet_hotel, container, false)

        val hotelNameTextView: TextView = view.findViewById(R.id.hotelNameTextView)
        val hotelRatingTextView: TextView = view.findViewById(R.id.hotelRatingTextView)
        val hotelPriceTextView: TextView = view.findViewById(R.id.hotelPriceTextView)

        hotelNameTextView.text = hotel.name
        hotelRatingTextView.text = "Rating: ${hotel.rating}"
        hotelPriceTextView.text = hotel.price

        return view
    }
}
