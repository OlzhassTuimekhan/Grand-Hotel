package kz.grand_hotel.ui.menu.ui.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        val recentAdapter = RecentSearchAdapter { item ->
            val query = if (item.placeLocation.isBlank())
                item.placeName else "${item.placeName}, ${item.placeLocation}"
            binding.searchView.setQuery(query, true)
        }
        val viewedAdapter = RecentlyViewedAdapter()

        binding.recentSearchesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentAdapter
        }
        binding.recentlyViewedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewedAdapter
        }

        searchViewModel.recentSearches.observe(viewLifecycleOwner) {
            recentAdapter.submitList(it)
        }
        searchViewModel.recentlyViewed.observe(viewLifecycleOwner) {
            viewedAdapter.submitList(it)
        }

        binding.backButton.setOnClickListener { requireActivity().onBackPressed() }
        binding.clearAllTextView.setOnClickListener { searchViewModel.clearAllSearches() }

        binding.searchView.setOnQueryTextListener(object:
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.addSearch(query)
                return true
            }
            override fun onQueryTextChange(newText: String)=false
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
