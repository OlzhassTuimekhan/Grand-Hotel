package kz.grand_hotel.ui.menu.ui.home.Hotel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentHotelDetailsBinding
import kz.grand_hotel.ui.menu.ui.home.HomeViewModel
import kz.grand_hotel.ui.menu.ui.home.cards.RecommendedAdapter


class HotelDetailsFragment : Fragment() {

    private var _binding: FragmentHotelDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var hotelViewModel: HotelDetailsViewModel


    private val recommendedAdapter by lazy {
        RecommendedAdapter { property ->
            val bundle = Bundle().apply {
                putInt   ("imageResId", property.image)
                putString("name",       property.name)
                putString("location",   property.location)
                putString("price",      property.price)
                putString("rating",     property.rating)
            }
            findNavController().navigate(
                R.id.action_hotelDetailsFragment_self,
                bundle
            )
        }
    }

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

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        hotelViewModel = ViewModelProvider(this).get(HotelDetailsViewModel::class.java)
        binding.hotelImageView.setImageResource(imageResId)
        binding.hotelNameTextView.text       = name
        binding.hotelLocationTextView.text   = location
        binding.priceBottomTextView.text     = price
        binding.hotelRatingTextView.text     = rating


        val reviewAdapter = ReviewAdapter()
        binding.reviewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
        }

        binding.recyclerViewRecommended.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = recommendedAdapter
        }

        homeViewModel.recommendedProperties.observe(viewLifecycleOwner) { recommendedProperties ->
            recommendedAdapter.submitList(recommendedProperties)
        }

        hotelViewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            reviewAdapter.submitList(reviews)
        }

        binding.seeAllFacilitiesTextView.setOnClickListener {
            findNavController().navigate(R.id.action_hotelDetailsFragment_to_facilitiesFragment)
        }

//
//        binding.hotelLocationImageView.setOnClickListener {
//            val bundle = bundleOf(
//                "target_lat" to hotel.locationLatLng.latitude,
//                "target_lng" to hotel.locationLatLng.longitude
//            )
//            findNavController().navigate(R.id.mapFragment, bundle)
//
//        }


        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }



    }

}