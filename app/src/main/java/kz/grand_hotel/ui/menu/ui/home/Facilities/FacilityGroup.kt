package kz.grand_hotel.ui.menu.ui.home.Facilities


data class FacilityGroup(
    val title: String,
    val children: List<String>,
    var isExpanded: Boolean = false
)

