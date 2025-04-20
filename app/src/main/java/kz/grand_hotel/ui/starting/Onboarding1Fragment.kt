package kz.grand_hotel.ui.starting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kz.grand_hotel.R
import android.widget.Button
import androidx.navigation.fragment.findNavController

class Onboarding1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding1, container, false)

        // Находим кнопку и устанавливаем обработчик
        val buttonContinue = view.findViewById<Button>(R.id.buttonContinue1)

        buttonContinue.setOnClickListener {
            // Переходим на следующий фрагмент
            findNavController().navigate(R.id.action_onboarding1_to_onboarding2)
        }

        return view
    }
}
