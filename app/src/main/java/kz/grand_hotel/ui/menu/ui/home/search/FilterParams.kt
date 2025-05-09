package kz.grand_hotel.ui.menu.ui.home.search

data class FilterParams(
    val minPrice: Int = 0,
    val maxPrice: Int = 80,
    val instantBookOnly: Boolean = false,
    val locations: Set<String> = emptySet(),
    val facilities: Set<String> = emptySet(),
    val ratings: Set<Int> = emptySet()
)