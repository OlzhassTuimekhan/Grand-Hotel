package kz.grand_hotel.ui.menu.ui.home

import com.google.android.gms.maps.model.LatLng

data class Hotels (
    val id: Int,
    val name: String,
    val location: String,
    val locationLatLng: LatLng,
    val rating: String,
    val price: String,
    val image: Int
)


