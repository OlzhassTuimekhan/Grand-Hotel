package kz.grand_hotel.ui.authorization.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Visibility
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentSignInBinding
import kz.grand_hotel.ui.GlobalData
import kz.grand_hotel.ui.authorization.register.ForgotFragment
import kz.grand_hotel.ui.authorization.register.SignUpFragment
import kz.grand_hotel.ui.menu.MenuActivity
import kz.grand_hotel.utils.showLoading
import kz.grand_hotel.utils.hideLoading
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false
    private val client = OkHttpClient()

//  Sign in
    //New Commit
    //New Commit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            signUpTextView.setOnClickListener {
                showSignUpFragment()
            }

            binding.signInButton.setOnClickListener {
                val email = binding.emailEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString()

                if (!isEmailValid(email)) {
                    binding.emailEditText.error = "Invalid email address"
                } else {
                    login(email, password)
                }
            }


            forgotPasswordTextView.setOnClickListener {
                showForgotFragment()
            }

            passwordVisibilityButton.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(
                    binding.passwordEditText,
                    binding.passwordVisibilityButton,
                    isPasswordVisible
                )
            }
        }

    }

    private fun login(email: String, password: String) {
        binding.errorTextView.visibility = View.GONE
        showLoading()

        val url = "https://grand-hotel-production.up.railway.app/api/signin"
        val jsonBody = JSONObject().apply {
            put("email", email)
            put("password", password)
        }
        val body = RequestBody.create("application/json".toMediaTypeOrNull(), jsonBody.toString())
        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Accept", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR", "${e.message}")
                requireActivity().runOnUiThread {
                    hideLoading()
                    binding.errorTextView.apply {
                        text = "Network error..."
                        visibility = View.VISIBLE
                    }
                    Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val bodyString = response.body?.string()
                requireActivity().runOnUiThread {
                    hideLoading()
                }

                if (response.isSuccessful) {
                    val token = JSONObject(bodyString).getString("token")
                    requireActivity().getSharedPreferences("user_preferences", AppCompatActivity.MODE_PRIVATE)
                        .edit().putString("token", token).apply()
                    GlobalData.token = token

                    requireActivity().runOnUiThread {
                        navigateToMenu()
                    }
                } else {
                    requireActivity().runOnUiThread {
                        binding.errorTextView.apply {
                            text = JSONObject(bodyString).optString("error", "Login failed")
                            visibility = View.VISIBLE
                        }
                        Toast.makeText(requireContext(), "Login failed: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }



    private fun showSignUpFragment() {
        val signUpFragment = SignUpFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, signUpFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun navigateToMenu() {
        val menuActivityIntent = Intent(this@SignInFragment.requireContext(), MenuActivity::class.java)
        startActivity(menuActivityIntent)
    }

    private fun showForgotFragment() {
        val forgotUpFragment = ForgotFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, forgotUpFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun togglePasswordVisibility(
        editText: EditText,
        icon: ImageView,
        isVisible: Boolean
    ) {
        if (isVisible) {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            icon.setImageResource(R.drawable.ic_eye_on) // иконка «глаз открыт»
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            icon.setImageResource(R.drawable.ic_eye_off) // иконка «глаз закрыт»
        }
        editText.setSelection(editText.text.length)
    }
}