package kz.grand_hotel.ui.authorization.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentOtpVerificationBinding
import kz.grand_hotel.ui.GlobalData
import kz.grand_hotel.ui.authorization.login.SignInFragment
import kz.grand_hotel.ui.menu.MenuActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class OtpVerificationFragment : Fragment() {

    private var _binding: FragmentOtpVerificationBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

    private var userId: Int = 0
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt("user_id", 0)
            email = it.getString("email","")
            password = it.getString("password","")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // переход по полю к полю
            otpDigit1TextView.addTextChangedListener(genericWatcher { otpDigit2TextView.requestFocus() })
            otpDigit2TextView.addTextChangedListener(genericWatcher { otpDigit3TextView.requestFocus() })
            otpDigit3TextView.addTextChangedListener(genericWatcher { otpDigit4TextView.requestFocus() })

            continueButton.setOnClickListener {
                val code = listOf(
                    otpDigit1TextView.text.toString(),
                    otpDigit2TextView.text.toString(),
                    otpDigit3TextView.text.toString(),
                    otpDigit4TextView.text.toString()
                ).joinToString("")

                if (code.length < 4) {
                    Toast.makeText(requireContext(), "Введите полный код", Toast.LENGTH_SHORT).show()
                } else {
                    verifyRegistrationOtp(code)
                }
            }

            backButton.setOnClickListener { parentFragmentManager.popBackStack() }
        }
    }

    private fun genericWatcher(onComplete: () -> Unit) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 1) onComplete()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun verifyRegistrationOtp(otp: String) {
        val url = "${GlobalData.ip}verify-registration-otp"

        val json = JSONObject().apply {
            put("user_id", userId)
            put("otp", otp)
        }
        Log.e("OTP", "OTP: $otp")
        val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OTP", "Network error: ${e.message}")
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val respString = response.body?.string()
                if (response.isSuccessful) {
                    val jsonResp = JSONObject(respString)
                    val token = jsonResp.getString("token")    // Bearer ...

                    requireActivity().getSharedPreferences("user_preferences", 0)
                        .edit().putString("token", token).apply()
                    GlobalData.token = token

                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Успешно! Вы в системе.", Toast.LENGTH_SHORT).show()

                        val intent = Intent(requireContext(), MenuActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    Log.e("OTP", "Invalid OTP ${response.code}: $respString")
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Неверный код", Toast.LENGTH_SHORT).show()
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
