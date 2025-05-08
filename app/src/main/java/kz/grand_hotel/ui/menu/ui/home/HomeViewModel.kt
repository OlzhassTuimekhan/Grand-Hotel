package kz.grand_hotel.ui.menu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kz.grand_hotel.R
import kz.grand_hotel.ui.menu.ui.home.Hotel.Hotels
import kz.grand_hotel.ui.menu.ui.home.Hotel.Review

class HomeViewModel : ViewModel() {

    private val _properties = MutableLiveData<List<Hotels>>()
    val properties: LiveData<List<Hotels>> = _properties

    private val _recommendedProperties = MutableLiveData<List<Hotels>>()
    val recommendedProperties: LiveData<List<Hotels>> = _recommendedProperties

    private val _hotels = MutableLiveData<List<Hotels>>()
    val hotels: LiveData<List<Hotels>> = _hotels

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    init {
        loadProperties()
    }

    private fun loadProperties() {

        val propertiesList = listOf(
            Hotels(1, "Novotel", "Almaty, KZ", LatLng(43.242284, 76.9575585), "4.7", "$320/night", R.drawable.ic_hotel1),
            Hotels(2, "hotel 'Almaty'", "Almaty, KZ", LatLng(43.2500694, 76.9270366), "4.5", "$280/night", R.drawable.ic_hotel2),
            Hotels(2, "Hotel 'Kazakhstan'", "Almaty, KZ", LatLng(43.2454565, 76.9423474), "4.0", "$250/night", R.drawable.ic_hotel3)
        )

        val hotelsList = listOf(
            Hotels(1, "Novotel", "Almaty, KZ", LatLng(43.242284, 76.9575585), "4.7", "$320/night", R.drawable.ic_hotel1),
            Hotels(2, "hotel 'Almaty'", "Almaty, KZ", LatLng(43.2500694, 76.9270366), "4.5", "$280/night", R.drawable.ic_hotel2),
            Hotels(2, "Hotel 'Kazakhstan'", "Almaty, KZ", LatLng(43.2454565, 76.9423474), "4.0", "$250/night", R.drawable.ic_hotel3)
        )

        _hotels.value = hotelsList
        _properties.value = propertiesList
        _recommendedProperties.value = listOf(
            Hotels(1, "Novotel", "Almaty, KZ", LatLng(43.242284, 76.9575585), "4.7", "$320/night", R.drawable.ic_hotel1),
            Hotels(2, "hotel 'Almaty'", "Almaty, KZ", LatLng(43.2500694, 76.9270366), "4.5", "$280/night", R.drawable.ic_hotel2),
            Hotels(2, "Hotel 'Kazakhstan'", "Almaty, KZ", LatLng(43.2454565, 76.9423474), "4.0", "$250/night", R.drawable.ic_hotel3)
        )

        _reviews.value = listOf(
            Review(
                avatarResId = R.drawable.ic_profile_dolares,
                authorName  = "Kim Borrdy",
                rating      = 4.5f,
                comment     = "Amazing! The room is better than the picture. Thanks for amazing experience!"
            ),
            Review(
                avatarResId = R.drawable.ic_profile_dolares,
                authorName  = "Mirai Kamazuki",
                rating      = 5.0f,
                comment     = "The service is on point, and I really like the facilities. Good job!"
            )
        )

    }

}