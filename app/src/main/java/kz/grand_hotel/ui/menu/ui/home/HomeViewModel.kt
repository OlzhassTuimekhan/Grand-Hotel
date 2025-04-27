package kz.grand_hotel.ui.menu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.grand_hotel.R

class HomeViewModel : ViewModel() {

    private val _properties = MutableLiveData<List<Property>>()
    val properties: LiveData<List<Property>> = _properties

    private val _recommendedProperties = MutableLiveData<List<Property>>()
    val recommendedProperties: LiveData<List<Property>> = _recommendedProperties

    init {
        loadProperties()
    }

    private fun loadProperties() {

        val propertiesList = listOf(
            Property(R.drawable.ic_onboarding1, "The Horizon Retreat", "Los Angeles, CA", "$480/night", "4.5"),
            Property(R.drawable.ic_onboarding2, "The Horizon Retreat", "Los Angeles, CA", "$480/night", "4.5"),
            Property(R.drawable.ic_onboarding3, "The Horizon Retreat", "Los Angeles, CA", "$480/night", "4.5"),)

        _properties.value = propertiesList

        _recommendedProperties.value = listOf(
            Property(R.drawable.ic_onboarding1,"Ocean Breeze Resort", "Miami, FL", "$480/night", "4.5"),
            Property(R.drawable.ic_onboarding2,"The Horizon Retreat", "Denver, CO", "$480/night", "4.2"),
        )
    }



}