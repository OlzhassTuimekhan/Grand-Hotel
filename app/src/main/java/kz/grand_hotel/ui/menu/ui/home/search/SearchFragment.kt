package kz.grand_hotel.ui.menu.ui.home.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentSearchBinding
import kz.grand_hotel.ui.menu.ui.home.HomeViewModel
import kz.grand_hotel.ui.menu.ui.home.Hotel.HotelDetailsViewModel
import kz.grand_hotel.ui.menu.ui.home.cards.RecommendedAdapter

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var hotelViewModel: HotelDetailsViewModel
    private lateinit var homeViewModel: HomeViewModel

    private val recentlyViewed by lazy {
        RecentlyViewedAdapter { property ->
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
                R.id.action_searchFragment_to_hotelDetailsFragment,
                bundle
            )
        }
    }

    private val resultsAdapter by lazy {
        RecentlyViewedAdapter { hotel ->
            searchViewModel.addToRecentlyViewed(hotel)

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
                R.id.action_searchFragment_to_hotelDetailsFragment,
                bundle
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(requireContext()))[SearchViewModel::class.java]
        hotelViewModel = ViewModelProvider(this).get(HotelDetailsViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        val recentAdapter = RecentSearchAdapter { item ->
            val query = if (item.placeLocation.isBlank())
                item.placeName else "${item.placeName}, ${item.placeLocation}"
            binding.searchView.setQuery(query, true)
        }

        binding.recentSearchesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentAdapter
        }

        searchViewModel.recentSearches.observe(viewLifecycleOwner) {
            recentAdapter.submitList(it)
        }

        binding.recentlyViewedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = recentlyViewed
        }
        searchViewModel.recentlyViewed.observe(viewLifecycleOwner) { recentlyViewedList ->
            recentlyViewed.submitList(recentlyViewedList)
        }

        binding.resultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter       = resultsAdapter
            visibility    = View.GONE
        }


        binding.backButton.setOnClickListener { requireActivity().onBackPressed() }
        binding.clearAllTextView.setOnClickListener { searchViewModel.clearAllSearches() }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.addSearch(query)

                binding.searchView.clearFocus()

                showResultsFor(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isBlank()) {
                    showRecentState()
                } else {
                    showResultsFor(newText)
                }
                return true
            }
        })
    }
    private fun showResultsFor(text: String) {
        val filtered = homeViewModel.hotels.value
            .orEmpty()
            .filter {
                it.name.contains(text, ignoreCase = true) ||
                        it.location.contains(text, ignoreCase = true)
            }

        binding.apply {
            recentSearchesRecyclerView.visibility   = View.GONE
            clearAllTextView.visibility             = View.GONE
            recentlyViewedRecyclerView.visibility   = View.GONE
            seeAllRecentlyViewedTextView.visibility = View.GONE

            resultsRecyclerView.visibility          = View.VISIBLE
        }
        resultsAdapter.submitList(filtered)
    }

    private fun showRecentState() {
        binding.apply {
            resultsRecyclerView.visibility          = View.GONE
            recentSearchesRecyclerView.visibility   = View.VISIBLE
            clearAllTextView.visibility             = View.VISIBLE
            recentlyViewedRecyclerView.visibility   = View.VISIBLE
            seeAllRecentlyViewedTextView.visibility = View.VISIBLE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
