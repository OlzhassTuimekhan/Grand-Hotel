package kz.grand_hotel.ui.menu.ui.home.Facilities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kz.grand_hotel.databinding.FragmentFacilitiesBinding
import kz.grand_hotel.ui.menu.ui.home.Hotel.HotelDetailsViewModel


class FacilitiesFragment : Fragment() {

    private var _binding: FragmentFacilitiesBinding? = null
    private val binding get() = _binding!!

    private val hotelViewModel by lazy {
        ViewModelProvider(requireActivity())[HotelDetailsViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFacilitiesBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.facilitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        hotelViewModel.facilities.observe(viewLifecycleOwner) { groups ->
            binding.facilitiesRecyclerView.adapter = FacilityGroupAdapter(groups)
        }


        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}