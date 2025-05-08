package kz.grand_hotel.ui.menu.ui.home.Facilities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kz.grand_hotel.databinding.FragmentFacilitiesBinding


class FacilitiesFragment : Fragment() {

    private var _binding: FragmentFacilitiesBinding? = null
    private val binding get() = _binding!!

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

        val data = listOf(
            FacilityGroup(
                title = "Food and Drink",
                children = listOf(
                    "A la carte dinner",
                    "A la carte lunch",
                    "Breakfast",
                    "Vegetarian meal"
                )
            ),
            FacilityGroup(
                title = "Transportation",
                children = listOf(
                    "Airport shuttle",
                    "Car rental",
                    "Taxi service",
                    "Shuttle service",
                    "Airport drop-off"
                )
            ),

        )

        binding.facilitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = FacilityGroupAdapter(data)
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