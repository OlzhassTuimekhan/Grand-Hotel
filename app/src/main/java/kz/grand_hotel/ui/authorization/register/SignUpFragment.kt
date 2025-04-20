package kz.grand_hotel.ui.authorization.register

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.os.bundleOf
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentSignInBinding
import kz.grand_hotel.databinding.FragmentSignUpBinding
import kz.grand_hotel.ui.authorization.login.SignInFragment


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
            signInTextView.setOnClickListener {
                showSignInFragment()
            }

            createAccountButton.setOnClickListener {
                val email = binding.emailEditText.text.toString().trim()

                if (!isEmailValid(email)) {
                    binding.emailEditText.error = "Invalid email address"
                } else if (passwordEditText.text.toString() != confirmPasswordEditText.text.toString()) {
                    binding.confirmPasswordEditText.error = "Passwords do not match"
                }
                else {
                    val email = emailEditText.text.toString()
                    val password = passwordEditText.text.toString()
                    bundleOf("email" to email, "password" to password)
                    val OtpVerificationFragment = OtpVerificationFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.container, OtpVerificationFragment)
                        ?.addToBackStack(null)
                        ?.commit()
//                    login(email, password)
                }
            }

            passwordVisibilityButton.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(
                    binding.passwordEditText,
                    binding.passwordVisibilityButton,
                    isPasswordVisible
                )
            }

            confirmPasswordVisibilityButton.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(
                    binding.confirmPasswordEditText,
                    binding.confirmPasswordVisibilityButton,
                    isPasswordVisible
                )
            }
        }
    }

    private fun showSignInFragment() {
        val signInFragment = SignInFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, signInFragment)
            ?.addToBackStack(null)
            ?.commit()
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

    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}