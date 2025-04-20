package kz.grand_hotel.ui.authorization.login

import android.content.Intent
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
import android.widget.TextView
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentSignInBinding
import kz.grand_hotel.ui.authorization.register.ForgotFragment
import kz.grand_hotel.ui.authorization.register.SignUpFragment
import kz.grand_hotel.ui.menu.MenuActivity
import kz.grand_hotel.ui.starting.GetStartedActivity

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false


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

            signInButton.setOnClickListener {
                val email = binding.emailEditText.text.toString().trim()

                if (!isEmailValid(email)) {
                    binding.emailEditText.error = "Invalid email address"
                } else {
                    val email = emailEditText.text.toString()
                    val password = passwordEditText.text.toString()
//                    login(email, password)
                    val menuActivityIntent = Intent(this@SignInFragment.requireContext(), MenuActivity::class.java)
                    startActivity(menuActivityIntent)


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

    private fun showSignUpFragment() {
        val signUpFragment = SignUpFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, signUpFragment)
            ?.addToBackStack(null)
            ?.commit()
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