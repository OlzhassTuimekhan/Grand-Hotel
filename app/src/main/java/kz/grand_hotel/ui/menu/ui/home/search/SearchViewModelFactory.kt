package kz.grand_hotel.ui.menu.ui.home.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kz.grand_hotel.data.SearchPrefs

class SearchViewModelFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(SearchPrefs(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
