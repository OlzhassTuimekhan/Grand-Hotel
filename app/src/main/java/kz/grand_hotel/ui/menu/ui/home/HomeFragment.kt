package kz.grand_hotel.ui.menu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var recommendedAdapter: RecommendedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        propertyAdapter = PropertyAdapter()
        recommendedAdapter = RecommendedAdapter()


        binding.recyclerViewMostPopular.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.recyclerViewMostPopular.adapter = propertyAdapter

        binding.recyclerViewRecommended.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerViewRecommended.adapter = recommendedAdapter

        homeViewModel.properties.observe(viewLifecycleOwner) { properties ->
            propertyAdapter.submitList(properties)
        }

        homeViewModel.recommendedProperties.observe(viewLifecycleOwner) { recommendedProperties ->
            recommendedAdapter.submitList(recommendedProperties)
        }

        binding.hotelNearYou.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_mapFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}