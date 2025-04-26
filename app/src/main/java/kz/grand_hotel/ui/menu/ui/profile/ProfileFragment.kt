package kz.grand_hotel.ui.menu.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentHomeBinding
import kz.grand_hotel.databinding.FragmentProfileBinding
import kz.grand_hotel.ui.authorization.AuthorizationActivity
import kz.grand_hotel.ui.authorization.login.SignInFragment
import kz.grand_hotel.ui.menu.ui.home.HomeViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            profileTextView.setOnClickListener {

            }

            logoutButton.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                val dialogView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_log_out, null)
                builder.setView(dialogView)
                val dialog = builder.create()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setCancelable(false)

                val logoutButton = dialogView.findViewById<Button>(R.id.logoutButton)
                logoutButton.setOnClickListener {
                    dialog.dismiss()
                    val intent = Intent(requireContext(), AuthorizationActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
                cancelButton.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }

            editProfileImageView.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_personal_info)
            }
            notificationLinearLayout.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_notifications)
            }
            securityLinearLayout.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_security)
            }
            languagesLinearLayout.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_languages)
            }
            helpSupportLinearLayout.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_support)
            }


        }
    }

}