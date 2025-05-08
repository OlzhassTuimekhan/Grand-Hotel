package kz.grand_hotel.ui.menu.ui.home.Hotel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentReviewsBinding


class ReviewsFragment : Fragment() {

    private var _binding: FragmentReviewsBinding? = null
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
        _binding = FragmentReviewsBinding.inflate(inflater, container, false )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reviewAdapter = ReviewAdapter()
        binding.reviewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
        }

        hotelViewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            reviewAdapter.submitList(reviews)

            val total = reviews.size
            binding.basedOnTextView.text = "Based on $total reviews"

            val avg = if (total > 0) reviews.map { it.rating }.average().toFloat() else 0f
            binding.overallRatingTextView.text = String.format("%.1f", avg)

            val stars = listOf(
                binding.star1, binding.star2,
                binding.star3, binding.star4, binding.star5
            )
            val filled = avg.toInt()
            stars.forEachIndexed { idx, iv ->
                if (idx < filled) {
                    iv.setImageResource(R.drawable.ic_star_filled)
                } else {
                    iv.setImageResource(R.drawable.ic_star_outline)
                }
            }

            val counts = reviews.groupingBy { it.rating.toInt() }.eachCount()
            fun pct(star: Int) = (counts[star]?.times(100) ?: 0) / total

            binding.progressBar1.progress = pct(1)
            binding.progressBar2.progress = pct(2)
            binding.progressBar3.progress = pct(3)
            binding.progressBar4.progress = pct(4)
            binding.progressBar5.progress = pct(5)
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