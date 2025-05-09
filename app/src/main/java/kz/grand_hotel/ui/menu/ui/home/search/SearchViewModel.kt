package kz.grand_hotel.ui.menu.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kz.grand_hotel.R
import kz.grand_hotel.ui.menu.ui.home.Hotel.Hotels

class SearchViewModel : ViewModel() {

    private val _recentSearches = MutableLiveData<List<RecentSearch>>(emptyList())
    val recentSearches: LiveData<List<RecentSearch>> = _recentSearches


    private val _recentlyViewed = MutableLiveData<List<Hotels>>(listOf(
        Hotels(
            id = 1,
            name = "The Ritz-Carlton, Almaty",
            location = "Prospekt Al-Farabi 77, Almaty, Kazakhstan",
            locationLatLng = LatLng(43.248300, 76.923400),
            rating = "4.8",
            price = "$350/night",
            image = R.drawable.ic_hotel1
        ),
        Hotels(
            id = 2,
            name = "InterContinental Almaty",
            location = "109 Abaya Ave, Almaty, Kazakhstan",
            locationLatLng = LatLng(43.249200, 76.924600),
            rating = "4.6",
            price = "$300/night",
            image = R.drawable.ic_hotel2
        ),
    ))

    val recentlyViewed: LiveData<List<Hotels>> = _recentlyViewed

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
