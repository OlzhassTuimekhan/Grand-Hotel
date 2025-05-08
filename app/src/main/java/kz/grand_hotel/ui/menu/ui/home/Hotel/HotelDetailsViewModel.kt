package kz.grand_hotel.ui.menu.ui.home.Hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.grand_hotel.R
import kz.grand_hotel.ui.menu.ui.home.Facilities.FacilityGroup

class HotelDetailsViewModel: ViewModel()  {

    private val _hotel = MutableLiveData<Hotels>()
    val hotel: LiveData<Hotels> = _hotel

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    private val _facilities = MutableLiveData<List<FacilityGroup>>()
    val facilities: LiveData<List<FacilityGroup>> = _facilities

    init {
        loadData()
    }

    private fun loadData() {
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
            ),
            Review(
                avatarResId = R.drawable.ic_profile_dolares,
                authorName  = "Luca Fernandes",
                rating      = 4.0f,
                comment     = "Very comfortable stay, breakfast was delicious. Parking could be improved."
            ),
            Review(
                avatarResId = R.drawable.ic_profile_dolares,
                authorName  = "Sofia Müller",
                rating      = 4.8f,
                comment     = "Staff were extremely friendly and helpful. Room was spotless!"
            ),
            Review(
                avatarResId = R.drawable.ic_profile_dolares,
                authorName  = "Arjun Patel",
                rating      = 3.9f,
                comment     = "Good value for money. Wi-Fi was a bit slow at times."
            )
        )


        _facilities.value = listOf(
            FacilityGroup(
                title = "Food and Drink",
                children = listOf(
                    "A la carte dinner",
                    "A la carte lunch",
                    "Breakfast",
                    "Vegetarian meal"
                )
            ),
            FacilityGroup(
                title = "Transportation",
                children = listOf(
                    "Airport shuttle",
                    "Car rental",
                    "Taxi service",
                    "Shuttle service",
                    "Airport drop-off"
                )
            ),
            FacilityGroup(
                title = "General",
                children = listOf(
                    "Free parking",
                    "Non-smoking rooms",
                    "Pet friendly",
                    "Air conditioning",
                    "Lift"
                )
            ),
            FacilityGroup(
                title = "Hotel Service",
                children = listOf(
                    "24-hour front desk",
                    "Concierge service",
                    "Laundry service",
                    "Room service",
                    "Wake-up call"
                )
            ),
            FacilityGroup(
                title = "Business Facilities",
                children = listOf(
                    "Business centre",
                    "Meeting rooms",
                    "Fax/photocopying",
                    "Projector",
                    "Computer station"
                )
            ),
            FacilityGroup(
                title = "Nearby Facilities",
                children = listOf(
                    "Restaurant",
                    "Bar",
                    "Shopping mall",
                    "Park",
                    "Museum"
                )
            ),
            FacilityGroup(
                title = "Kids",
                children = listOf(
                    "Kids’ club",
                    "Indoor play area",
                    "Outdoor playground"
                )
            ),
            FacilityGroup(
                title = "Connectivity",
                children = listOf(
                    "Free Wi-Fi",
                    "Wired internet",
                    "Public computers"
                )
            ),
            FacilityGroup(
                title = "Public Facilities",
                children = listOf(
                    "Safety deposit box",
                    "ATM on site",
                    "Ticket service",
                    "Shared lounge/TV area",
                    "Vending machine"
                )
            )
        )



    }


}