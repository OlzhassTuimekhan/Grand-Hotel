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
            Hotels(
                id = 3,
                name = "Grand Hotel Tien Shan",
                location = "77 Dostyk Ave, Almaty, Kazakhstan",
                locationLatLng = LatLng(43.231000, 76.947700),
                rating = "4.3",
                price = "$220/night",
                image = R.drawable.ic_hotel3
            ),
            Hotels(
                id = 4,
                name = "Radisson Blu Hotel, Almaty",
                location = "137 Abylay Khan Ave, Almaty, Kazakhstan",
                locationLatLng = LatLng(43.237500, 76.945700),
                rating = "4.4",
                price = "$260/night",
                image = R.drawable.ic_hotel1
            ),
            Hotels(
                id = 5,
                name = "DoubleTree by Hilton Almaty",
                location = "85 Al-Farabi Ave, Almaty, Kazakhstan",
                locationLatLng = LatLng(43.253300, 76.942500),
                rating = "4.2",
                price = "$200/night",
                image = R.drawable.ic_hotel2
            )
        )


        _hotels.value = hotelsList
        _properties.value = propertiesList
        _recommendedProperties.value = listOf(
            Hotels(1, "Novotel", "Almaty, KZ", LatLng(43.242284, 76.9575585), "4.7", "$320/night", R.drawable.ic_hotel1),
            Hotels(2, "hotel 'Almaty'", "Almaty, KZ", LatLng(43.2500694, 76.9270366), "4.5", "$280/night", R.drawable.ic_hotel2),
            Hotels(2, "Hotel 'Kazakhstan'", "Almaty, KZ", LatLng(43.2454565, 76.9423474), "4.0", "$250/night", R.drawable.ic_hotel3)
        )

    }

}