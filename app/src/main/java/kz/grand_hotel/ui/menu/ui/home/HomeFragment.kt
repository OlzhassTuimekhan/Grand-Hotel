package kz.grand_hotel.ui.menu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentHomeBinding
import kz.grand_hotel.ui.menu.ui.home.cards.PropertyAdapter
import kz.grand_hotel.ui.menu.ui.home.cards.RecommendedAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    private val propertyAdapter by lazy {
        PropertyAdapter { property ->
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
                R.id.action_navigation_home_to_hotelDetailsFragment
                , bundle
            )
        }
    }
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.recyclerViewMostPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = propertyAdapter
        }

        binding.recyclerViewRecommended.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = recommendedAdapter
        }

        homeViewModel.properties.observe(viewLifecycleOwner) { properties ->
            propertyAdapter.submitList(properties)
        }
        homeViewModel.recommendedProperties.observe(viewLifecycleOwner) { recommendedProperties ->
            recommendedAdapter.submitList(recommendedProperties.take(2))
        }

        binding.hotelNearYou.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_mapFragment)
        }

        binding.searchImageView.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_searchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
