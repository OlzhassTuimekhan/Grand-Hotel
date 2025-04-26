package kz.grand_hotel.ui.menu.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentChatBinding
import kz.grand_hotel.databinding.FragmentMessageBinding

class ChatFragment : Fragment() {


    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageAdapter: MessageAdapter

    // Используем ViewModel
    private lateinit var messageViewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

}