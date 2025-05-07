package kz.grand_hotel.ui.menu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kz.grand_hotel.R

class HomeViewModel : ViewModel() {

    private val _properties = MutableLiveData<List<Property>>()
    val properties: LiveData<List<Property>> = _properties

    private val _recommendedProperties = MutableLiveData<List<Property>>()
    val recommendedProperties: LiveData<List<Property>> = _recommendedProperties

    private val _hotels = MutableLiveData<List<HotelsInMap>>()
    val hotels: LiveData<List<HotelsInMap>> = _hotels

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    init {
        loadProperties()
    }

    private fun loadProperties() {

        val propertiesList = listOf(
            Property(1, R.drawable.ic_onboarding1, "Kazakhstan Hotel", "Almaty, KZ", "$480/night", "4.5"),
            Property(2, R.drawable.ic_onboarding2, "Almaty Hotel", "Almaty, KZ", "$250/night", "4.5"),
            Property(3, R.drawable.ic_onboarding3, "Novotel", "Almaty, KZ", "$310/night", "4.5"),)

        val hotelsList = listOf(
            HotelsInMap(1, "Novotel", "Almaty, KZ", LatLng(43.242284, 76.9575585), "4.7", "$320/night", R.drawable.ic_hotel1),
            HotelsInMap(2, "hotel 'Almaty'", "Almaty, KZ", LatLng(43.2500694, 76.9270366), "4.5", "$280/night", R.drawable.ic_hotel2),
            HotelsInMap(2, "Hotel 'Kazakhstan'", "Almaty, KZ", LatLng(43.2454565, 76.9423474), "4.0", "$250/night", R.drawable.ic_hotel3)
        )

        _hotels.value = hotelsList
        _properties.value = propertiesList
        _recommendedProperties.value = listOf(
            Property(1, R.drawable.ic_onboarding1,"Ocean Breeze Resort", "Miami, FL", "$480/night", "4.5"),
            Property(2, R.drawable.ic_onboarding2,"The Horizon Retreat", "Denver, CO", "$480/night", "4.2"),
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