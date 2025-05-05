package kz.grand_hotel.ui.authorization.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentForgotBinding
import kz.grand_hotel.ui.GlobalData
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException
import kz.grand_hotel.utils.hideLoading
import kz.grand_hotel.utils.showLoading

class ForgotFragment : Fragment() {

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            nextButton.setOnClickListener {
                val email = emailEditText.text.toString().trim()
                if (email.isEmpty()) {
                    emailEditText.error = "Email is required"
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.error = "Invalid email address"
                } else {
                    sendOtp(email)
                }
            }
        }
    }

    private fun sendOtp(email: String) {
        showLoading()
        val url = "${GlobalData.ip}send-otp"
        val jsonBody = JSONObject().apply {
            put("email", email)
        }
        val body = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            jsonBody.toString()
        )
        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ForgotFragment", "Network error: ${e.message}")
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
                }
                hideLoading()
            }

            override fun onResponse(call: Call, response: Response) {
                val respString = response.body?.string()
                if (response.isSuccessful) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "OTP sent to your email", Toast.LENGTH_SHORT).show()
                        val bundle = Bundle().apply {
                            putString("email", email)
                            putInt("user_id", JSONObject(respString).optInt("user_id", 0))
                        }
                        hideLoading()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, OtpResetFragment().apply { arguments = bundle })
                            .addToBackStack(null)
                            .commit()
                    }
                } else {
                    Log.e("ForgotFragment", "Error ${response.code}: $respString")
                    hideLoading()
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Failed to send OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
