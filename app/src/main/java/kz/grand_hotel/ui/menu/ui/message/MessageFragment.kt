package kz.grand_hotel.ui.menu.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentMessageBinding
import kz.grand_hotel.databinding.FragmentProfileBinding
import kz.grand_hotel.ui.menu.ui.profile.ProfileViewModel


class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageAdapter: MessageAdapter

    // Используем ViewModel
    private lateinit var messageViewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем ViewModel
        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)

        // Настроим RecyclerView
        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        messageAdapter = MessageAdapter()
        binding.messagesRecyclerView.adapter = messageAdapter

        // Наблюдаем за LiveData
        messageViewModel.messages.observe(viewLifecycleOwner) { messages ->
            messageAdapter.submitList(messages) // Обновляем список сообщений
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
