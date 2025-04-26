package kz.grand_hotel.ui.menu.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentNotificationsBinding
import kz.grand_hotel.databinding.FragmentPersonalInfoBinding
import kz.grand_hotel.databinding.FragmentSignInBinding


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

}