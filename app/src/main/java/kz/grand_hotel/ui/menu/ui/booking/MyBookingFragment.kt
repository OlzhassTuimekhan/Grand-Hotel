package kz.grand_hotel.ui.menu.ui.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentMyBookingBinding
import kz.grand_hotel.databinding.FragmentProfileBinding
import kz.grand_hotel.ui.menu.ui.profile.ProfileViewModel

class MyBookingFragment : Fragment() {


    private var _binding: FragmentMyBookingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myBookingViewModel =
            ViewModelProvider(this).get(MyBookingViewModel::class.java)

        _binding = FragmentMyBookingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}