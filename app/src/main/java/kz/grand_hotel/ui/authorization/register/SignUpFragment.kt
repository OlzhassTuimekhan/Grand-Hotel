package kz.grand_hotel.ui.authorization.register

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentSignUpBinding
import kz.grand_hotel.ui.GlobalData
import kz.grand_hotel.ui.authorization.login.SignInFragment
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false

    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            signInTextView.setOnClickListener { showSignInFragment() }

            createAccountButton.setOnClickListener {
                val name = fullNameEditText.text.toString().trim()
                val nickname = nicknameEditText.text.toString().trim()
                val email = emailEditText.text.toString().trim()
                val phone = phoneEditText.text.toString().trim()
                val password = passwordEditText.text.toString()
                val confirm = confirmPasswordEditText.text.toString()


                if (name.isEmpty()) {
                    fullNameEditText.error = "Enter your name"
                } else if (!isEmailValid(email)) {
                    emailEditText.error = "Invalid email address"
                } else if (password != confirm) {
                    confirmPasswordEditText.error = "Passwords do not match"
                } else {
                    registerUser(name, nickname, email, phone, password, confirm)
                }
            }

            passwordVisibilityButton.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(passwordEditText, passwordVisibilityButton, isPasswordVisible)
            }
            confirmPasswordVisibilityButton.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(confirmPasswordEditText, confirmPasswordVisibilityButton, isPasswordVisible)
            }

            val etNumber: EditText = binding.phoneEditText

            etNumber.addTextChangedListener(object : TextWatcher {
                private var mFormatting = false
                private var mClearing = false
                private var lastLength = 0

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    lastLength = s?.length ?: 0
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (mFormatting || mClearing) {
                        return
                    }

                    mFormatting = true

                    val formattedNumber = formatPhoneNumber(s.toString())
                    s?.replace(0, s.length, formattedNumber)

                    mFormatting = false
                }

                private fun formatPhoneNumber(number: String): String {
                    var digits = number.replace("[^\\d]".toRegex(), "")

                    if (digits.startsWith("7")) {
                        digits = "7" + digits.substring(1)
                    }

                    return when {
                        digits.length <= 1 -> "+" + digits
                        digits.length <= 4 -> "+" + digits.substring(0, 1) + " (" + digits.substring(1)
                        digits.length <= 7 -> "+" + digits.substring(0, 1) + " (" + digits.substring(1, 4) + ") " + digits.substring(4)
                        digits.length <= 9 -> "+" + digits.substring(0, 1) + " (" + digits.substring(1, 4) + ") " + digits.substring(4, 7) + "-" + digits.substring(7)
                        digits.length <= 11 -> "+" + digits.substring(0, 1) + " (" + digits.substring(1, 4) + ") " + digits.substring(4, 7) + "-" + digits.substring(7, 9) + "-" + digits.substring(9)
                        else -> "+" + digits.substring(0, 1) + " (" + digits.substring(1, 4) + ") " + digits.substring(4, 7) + "-" + digits.substring(7, 9) + "-" + digits.substring(9, 11)
                    }
                }
            })
        }
    }


    private fun registerUser(
        name: String,
        nickname: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String
    ) {
        val onlyDigits = phone.replace("\\D+".toRegex(), "")
        val normalizedDigits = if (onlyDigits.startsWith("7")) {
            onlyDigits
        } else {
            "7$onlyDigits"
        }
        val phoneForApi = "+$normalizedDigits"

        val url = "${GlobalData.ip}signup"

        val jsonBody = JSONObject().apply {
            put("name", name)
            put("nickname", nickname)
            put("email", email)
            put("phone_number", phoneForApi)
            put("password", password)
            put("password_confirmation", passwordConfirmation)
        }

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, jsonBody.toString())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val respString = response.body?.string()

                if (response.isSuccessful) {
                    val json = JSONObject(respString)
                    val userId = json.getInt("user_id")
                    Log.d("SignUp", "Registered user_id=$userId")

                    requireActivity().runOnUiThread {
                        val bundle = bundleOf(
                            "user_id" to userId,
                            "email" to email,
                            "password" to password
                        )
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, OtpVerificationFragment().apply { arguments = bundle })
                            .addToBackStack(null)
                            .commit()
                    }
                } else {

                    Log.e("SignUp", "Error ${response.code}: $respString")
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("SignUp", "Request failed: ${e.message}")
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Network error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showSignInFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, SignInFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun isEmailValid(email: String): Boolean =
        !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun togglePasswordVisibility(
        editText: EditText,
        icon: ImageView,
        isVisible: Boolean
    ) {
        if (isVisible) {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            icon.setImageResource(R.drawable.ic_eye_on)
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            icon.setImageResource(R.drawable.ic_eye_off)
        }
        editText.setSelection(editText.text.length)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
