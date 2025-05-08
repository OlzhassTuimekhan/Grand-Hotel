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
import kz.grand_hotel.databinding.FragmentRecommendationBinding
import kz.grand_hotel.ui.menu.ui.home.HomeViewModel
import kz.grand_hotel.ui.menu.ui.home.cards.RecommendedAdapter


class RecommendationFragment : Fragment() {

    private var _binding: FragmentRecommendationBinding? = null
    private val binding get() = _binding!!
    private lateinit var hotelViewModel: HotelDetailsViewModel
    private lateinit var homeViewModel: HomeViewModel

        private val recommendedAdapter by lazy {
        RecommendedAdapter { property ->
            val bundle = Bundle().apply {
                putInt   ("id", property.id)
                putInt   ("imageResId", property.image)
                putString("name",       property.name)
                putString("location",   property.location)
                putDouble("latitude",   property.locationLatLng.latitude)
                putDouble("longitude",  property.locationLatLng.longitude)
                putString("price",      property.price)
                putString("rating",     property.rating)
            }
            findNavController().navigate(
                R.id.action_navigation_home_to_hotelDetailsFragment,
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotelViewModel = ViewModelProvider(this).get(HotelDetailsViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        binding.recommendedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = recommendedAdapter
        }
        homeViewModel.recommendedProperties.observe(viewLifecycleOwner) { recommendedProperties ->
            recommendedAdapter.submitList(recommendedProperties)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null


    }
}