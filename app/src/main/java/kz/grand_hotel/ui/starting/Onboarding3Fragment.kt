package kz.grand_hotel.ui.starting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kz.grand_hotel.R
import android.widget.Button
import android.widget.TextView
import kz.grand_hotel.ui.authorization.AuthorizationActivity

class Onboarding3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding3, container, false)

        val buttonContinue = view.findViewById<Button>(R.id.buttonContinue3)

        buttonContinue.setOnClickListener {
            // Переход на активность авторизации
            val intent = Intent(requireContext(), AuthorizationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }
}

