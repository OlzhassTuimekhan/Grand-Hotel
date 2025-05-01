package kz.grand_hotel.ui.menu.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentHomeBinding
import kz.grand_hotel.databinding.FragmentProfileBinding
import kz.grand_hotel.ui.GlobalData
import kz.grand_hotel.ui.authorization.AuthorizationActivity
import kz.grand_hotel.ui.authorization.login.SignInFragment
import kz.grand_hotel.ui.menu.ui.home.HomeViewModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

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
                    performLogout()


                    dialog.dismiss()
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

    private fun performLogout() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_preferences", AppCompatActivity.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        val url = "${GlobalData.ip}logout"
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            "{}"
        )

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $token")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val editor = sharedPreferences.edit()
                    editor.remove("token")
                    editor.apply()

                    val intent = Intent(requireContext(), AuthorizationActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    requireActivity().runOnUiThread {
                        Log.e("Log Out ", "Error with Log out: " )
                    }
                }
            }
        })
    }

}