package kz.grand_hotel.ui.authorization.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentOtpResetBinding
import kz.grand_hotel.ui.GlobalData
import kz.grand_hotel.utils.showLoading
import kz.grand_hotel.utils.hideLoading
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class OtpResetFragment : Fragment() {

    private var _binding: FragmentOtpResetBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

    private var userId: Int = 0
    private var emailFromForgot: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt("user_id") ?: 0
        emailFromForgot = arguments?.getString("email") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpResetBinding.inflate(inflater, container, false)
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

                val d1 = otpDigit1TextView.text.toString().trim()
                val d2 = otpDigit2TextView.text.toString().trim()
                val d3 = otpDigit3TextView.text.toString().trim()
                val d4 = otpDigit4TextView.text.toString().trim()

                if (d1.isEmpty() || d2.isEmpty() || d3.isEmpty() || d4.isEmpty()) {
                    Toast.makeText(requireContext(), "Введите полный код", Toast.LENGTH_SHORT).show()
                } else {
                    val code = d1 + d2 + d3 + d4
                    verifyOtp(code)
                }
            }

            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            resendCodeTextView.setOnClickListener {

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
        showLoading()
        binding.errorTextView.visibility = View.GONE

        val url = "${GlobalData.ip}verify-otp"
        val json = JSONObject().apply {
            put("user_id", userId)
            put("otp", otp)
        }
        val body = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            json.toString()
        )
        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    hideLoading()
                    Toast.makeText(requireContext(), "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val respString = response.body?.string().orEmpty()
                requireActivity().runOnUiThread {
                    hideLoading()
                    when (response.code) {
                        200 -> {
                            val jsonResp = JSONObject(respString)
                            val resetToken = jsonResp.optString("reset_token", "")
                            if (resetToken.isNotBlank()) {

                                val frag = CreateNewPasswordFragment().apply {
                                    arguments = Bundle().apply {
                                        putString("email", emailFromForgot)
                                        putString("reset_token", resetToken)
                                    }
                                }
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.container, frag)
                                    .addToBackStack(null)
                                    .commit()
                            } else {
                                Toast.makeText(requireContext(), "Нечего сохранять", Toast.LENGTH_SHORT).show()
                            }
                        }
                        422 -> {
                            val msg = JSONObject(respString)
                                .optString("message", "Неверный или просроченный код")
                            binding.errorTextView.text = msg
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
