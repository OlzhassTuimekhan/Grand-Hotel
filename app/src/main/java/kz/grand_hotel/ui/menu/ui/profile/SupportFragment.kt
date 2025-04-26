package kz.grand_hotel.ui.menu.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentSupportBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class SupportFragment : Fragment() {

    private var _binding: FragmentSupportBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: HelpAdapter
    private val helpItems = mutableListOf<HelpItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerViewHelp
        searchView = view.findViewById(R.id.searchView)

        helpItems.add(HelpItem("How can I book a room?", "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint."))
        helpItems.add(HelpItem("Where is the nearest airport?", "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint."))

        adapter = HelpAdapter(helpItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = helpItems.filter {
                    it.title.contains(newText.orEmpty(), ignoreCase = true)
                }
                recyclerView.adapter = HelpAdapter(filtered)
                return true
            }
        })

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
