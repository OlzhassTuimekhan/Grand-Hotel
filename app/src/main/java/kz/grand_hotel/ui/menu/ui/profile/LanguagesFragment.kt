package kz.grand_hotel.ui.menu.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentLanguagesBinding
class LanguagesFragment : Fragment() {

    private var _binding: FragmentLanguagesBinding? = null
    private val binding get() = _binding!!
    private lateinit var languageManager: LanguageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        languageManager = LanguageManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentLanguage = languageManager.getCurrentLanguage()
        updateCheckVisibility(currentLanguage)

        binding.englishUkLayout.setOnClickListener {
            updateCheckVisibility("en")
//            languageManager.setNewLocale(requireActivity(), "en")
        }

        binding.russianLayout.setOnClickListener {
            updateCheckVisibility("ru")
//            languageManager.setNewLocale(requireActivity(), "ru")
        }

        binding.kazakhLayout.setOnClickListener {
            updateCheckVisibility("kk")
//            languageManager.setNewLocale(requireActivity(), "kk")
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun updateCheckVisibility(selectedLanguage: String) {
        binding.englishUkLayout.findViewById<View>(R.id.checkImageView)?.visibility =
            if (selectedLanguage == "en") View.VISIBLE else View.INVISIBLE
        binding.russianLayout.findViewById<View>(R.id.checkImageView1)?.visibility =
            if (selectedLanguage == "ru") View.VISIBLE else View.INVISIBLE
        binding.kazakhLayout.findViewById<View>(R.id.checkImageView2)?.visibility =
            if (selectedLanguage == "kk") View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
