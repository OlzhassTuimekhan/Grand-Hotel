package kz.grand_hotel.ui.menu.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.grand_hotel.R

class MessageViewModel : ViewModel() {

    private val _messages = MutableLiveData<List<MessageItem>>()
    val messages: LiveData<List<MessageItem>> = _messages

    init {
        fillMessageList()
    }

    private fun fillMessageList() {
        val testMessageList = mutableListOf<MessageItem>()

        testMessageList.add(
            MessageItem(
                R.drawable.ic_profile_dolares,
                "Miss Dolores Schowalter",
                "Thank you! 😊",
                "7:12 AM",
                3
            )
        )
        testMessageList.add(
            MessageItem(
                R.drawable.ic_profile_dolares,
                "Lorena Farrell",
                "Yes! please take an order",
                "9:28 AM",
                0
            )
        )
        testMessageList.add(
            MessageItem(
                R.drawable.ic_profile_dolares,
                "Amos Hessel",
                "I think this one is good",
                "4:35 PM",
                0
            )
        )
        testMessageList.add(
            MessageItem(
                R.drawable.ic_profile_dolares,
                "Ollie Haley",
                "Wow, this is really epic",
                "8:12 PM",
                0
            )
        )
        testMessageList.add(
            MessageItem(
                R.drawable.ic_profile_dolares,
                "Traci Maggio",
                "omg, this is amazing",
                "10:22 PM",
                0
            )
        )
        testMessageList.add(
            MessageItem(
                R.drawable.ic_profile_dolares,
                "Mathew Konopelski",
                "wooooo",
                "yesterday",
                0
            )
        )
        testMessageList.add(
            MessageItem(
                R.drawable.ic_profile_dolares,
                "Miss Dolores Schowalter",
                "Thank you! 😊",
                "7:12 AM",
                3
            )
        )

        _messages.value = testMessageList
    }
}
