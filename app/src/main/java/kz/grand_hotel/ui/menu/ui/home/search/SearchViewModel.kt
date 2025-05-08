package kz.grand_hotel.ui.menu.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.grand_hotel.R

class SearchViewModel : ViewModel() {

    private val _recentSearches = MutableLiveData<List<RecentSearch>>(emptyList())
    val recentSearches: LiveData<List<RecentSearch>> = _recentSearches


    private val _recentlyViewed = MutableLiveData<List<RecentlyViewed>>(listOf(
        RecentlyViewed(1, R.drawable.ic_onboarding1, "Mystic Palms", "Palm Springs, CA", "$230/night", "4.0"),
        RecentlyViewed(2, R.drawable.ic_onboarding2, "Sapphire Cove Hotel", "Key West, FL", "$290/night", "3.8"),
        RecentlyViewed(3, R.drawable.ic_onboarding3, "Elysian Suites", "San Diego, CA", "$320/night", "3.8")
    ))
    
    val recentlyViewed: LiveData<List<RecentlyViewed>> = _recentlyViewed

    fun addSearch(query: String) {
        if (query.isBlank()) return
        val parts = query.split(",").map { it.trim() }
        val name = parts.getOrNull(0).orEmpty()
        val location = parts.getOrNull(1).orEmpty()
        val entry = RecentSearch(name, location)

        val updated = listOf(entry) + _recentSearches.value!!.filter { it.placeName != name }
        _recentSearches.value = updated.take(10)
    }

    fun clearAllSearches() {
        _recentSearches.value = emptyList()
    }
}
