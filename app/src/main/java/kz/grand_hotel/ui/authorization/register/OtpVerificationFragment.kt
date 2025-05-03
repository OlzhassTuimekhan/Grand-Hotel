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
import kz.grand_hotel.databinding.FragmentOtpVerificationBinding
import kz.grand_hotel.ui.GlobalData
import kz.grand_hotel.ui.menu.MenuActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class OtpVerificationFragment : Fragment() {

    private var _binding: FragmentOtpVerificationBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

    private var registrationToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationToken = arguments?.getString("registration_token") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            otpDigit1TextView.addTextChangedListener(genericWatcher { otpDigit2TextView.requestFocus() })
            otpDigit2TextView.addTextChangedListener(genericWatcher { otpDigit3TextView.requestFocus() })
            otpDigit3TextView.addTextChangedListener(genericWatcher { otpDigit4TextView.requestFocus() })

            continueButton.setOnClickListener {
                errorTextView.visibility = View.GONE
                val code = listOf(otpDigit1TextView, otpDigit2TextView, otpDigit3TextView, otpDigit4TextView)
                    .joinToString("") { it.text.toString() }

                if (code.length < 4) {
                    Toast.makeText(requireContext(), "Введите полный код", Toast.LENGTH_SHORT).show()
                } else {
                    verifyOtp(code)
                }
            }

            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun genericWatcher(onDone: () -> Unit) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 1) onDone()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun verifyOtp(otp: String) {
        val url = "https://grand-hotel-production.up.railway.app/api/register/verify-otp"
        val json = JSONObject().apply {
            put("registration_token", registrationToken)
            put("otp", otp)
        }

        val body = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            json.toString()
        )
        val req = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Accept", "application/json")
            .build()
        Log.d("RESPONSE BODY", "request: $req")

        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val resp = response.body?.string().orEmpty()
                requireActivity().runOnUiThread {
                    when (response.code) {
                        201 -> {
                            val j = JSONObject(resp)
                            val message = j.optString("message", "Успешно")
                            var token = j.optString("token", "")
                                .removePrefix("Bearer ").trim()

                            requireActivity()
                                .getSharedPreferences("user_preferences", 0)
                                .edit()
                                .putString("token", token)
                                .apply()
                            GlobalData.token = token

                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            startActivity(
                                Intent(requireContext(), MenuActivity::class.java).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                            )
                        }
                        422 -> {
                            val j = JSONObject(resp)
                            val err = j.optString("message", "Неверный или просроченный код")
                            binding.errorTextView.text = err
                            binding.errorTextView.visibility = View.VISIBLE
                        }
                        else -> {
                            Toast.makeText(
                                requireContext(),
                                "Ошибка ${response.code}: ${response.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
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
