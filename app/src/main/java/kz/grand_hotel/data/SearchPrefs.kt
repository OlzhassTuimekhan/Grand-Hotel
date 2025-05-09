package kz.grand_hotel.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.grand_hotel.ui.menu.ui.home.search.RecentSearch
import kz.grand_hotel.ui.menu.ui.home.Hotel.Hotels

private val Context.dataStore by preferencesDataStore("search_prefs")

class SearchPrefs(val context: Context) {
    companion object {
        private val KEY_RECENT_SEARCHES = stringPreferencesKey("recent_searches_json")
        private val KEY_RECENTLY_VIEWED  = stringPreferencesKey("recently_viewed_json")
    }

    private val gson = Gson()

    val recentSearchesFlow: Flow<List<RecentSearch>> =
        context.dataStore.data
            .map { prefs ->
                val json = prefs[KEY_RECENT_SEARCHES] ?: "[]"
                val type = object: TypeToken<List<RecentSearch>>() {}.type
                gson.fromJson<List<RecentSearch>>(json, type)
            }

    val recentlyViewedFlow: Flow<List<Hotels>> =
        context.dataStore.data
            .map { prefs ->
                val json = prefs[KEY_RECENTLY_VIEWED] ?: "[]"
                val type = object: TypeToken<List<Hotels>>() {}.type
                gson.fromJson<List<Hotels>>(json, type)
            }

    suspend fun saveRecentSearches(list: List<RecentSearch>) {
        val json = gson.toJson(list)
        context.dataStore.edit { prefs ->
            prefs[KEY_RECENT_SEARCHES] = json
        }
    }

    suspend fun saveRecentlyViewed(list: List<Hotels>) {
        val json = gson.toJson(list)
        context.dataStore.edit { prefs ->
            prefs[KEY_RECENTLY_VIEWED] = json
        }
    }
}
