package kz.grand_hotel.ui.menu.ui.home

import com.google.android.gms.maps.model.LatLng

data class Hotel(
    val name: String,
    val location: LatLng,
    val rating: Double,
    val price: String,
    val imageResId: Int
)
