package kz.grand_hotel.ui.menu.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentHomeBinding
import kz.grand_hotel.databinding.FragmentHotelDetailsBinding


class HotelDetailsFragment : Fragment() {

    private var _binding: FragmentHotelDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments()
        val imageResId = args.getInt("imageResId")
        val name       = args.getString("name")
        val location   = args.getString("location")
        val price      = args.getString("price")
        val rating     = args.getString("rating")

        binding.hotelImageView.setImageResource(imageResId)
        binding.hotelNameTextView.text       = name
        binding.hotelLocationTextView.text   = location
        binding.priceBottomTextView.text     = price
        binding.hotelRatingTextView.text     = rating

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

}