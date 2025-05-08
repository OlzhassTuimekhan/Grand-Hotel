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

            )


    }


}