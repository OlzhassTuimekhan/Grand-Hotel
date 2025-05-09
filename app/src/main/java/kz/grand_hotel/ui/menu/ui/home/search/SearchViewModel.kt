package kz.grand_hotel.ui.menu.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kz.grand_hotel.R
import kz.grand_hotel.data.SearchPrefs
import kz.grand_hotel.ui.menu.ui.home.Hotel.Hotels

class SearchViewModel(
    private val prefs: SearchPrefs
): ViewModel() {

    private val _recentSearches = MutableLiveData<List<RecentSearch>>(emptyList())
    val recentSearches: LiveData<List<RecentSearch>> = _recentSearches

    private val _recentlyViewed = MutableLiveData<List<Hotels>>(emptyList())
    val recentlyViewed: LiveData<List<Hotels>> = _recentlyViewed

    init {
        viewModelScope.launch {
            prefs.recentSearchesFlow.collect {
                _recentSearches.value = it
            }
        }
        viewModelScope.launch {
            prefs.recentlyViewedFlow.collect {
                _recentlyViewed.value = it
            }
        }
    }

    fun addSearch(query: String) {
        if (query.isBlank()) return
        val parts    = query.split(",").map { it.trim() }
        val entry    = RecentSearch(parts.getOrNull(0).orEmpty(),
            parts.getOrNull(1).orEmpty())
        val updated  = listOf(entry) + (_recentSearches.value ?: emptyList())
            .filter { it.placeName != entry.placeName }
            .take(9)
        _recentSearches.value = updated
        viewModelScope.launch { prefs.saveRecentSearches(updated) }
    }

    fun addToRecentlyViewed(hotel: Hotels) {
        val current = (_recentlyViewed.value ?: emptyList()).toMutableList()
        current.removeAll { it.id == hotel.id }
        current.add(0, hotel)
        val updated = current.take(10)
        _recentlyViewed.value = updated

        viewModelScope.launch { prefs.saveRecentlyViewed(updated) }
    }

    fun clearAllSearches() {
        _recentSearches.value = emptyList()
        viewModelScope.launch { prefs.saveRecentSearches(emptyList()) }
    }
}
