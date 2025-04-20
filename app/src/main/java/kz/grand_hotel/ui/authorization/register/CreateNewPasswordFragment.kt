package kz.grand_hotel.ui.authorization.register

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentCreateNewPasswordBinding
import kz.grand_hotel.databinding.FragmentSignInBinding
import kz.grand_hotel.ui.authorization.login.SignInFragment


class CreateNewPasswordFragment : Fragment() {

    private var _binding: FragmentCreateNewPasswordBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNewPasswordBinding.inflate(inflater, container, false)
        return binding.root    }

    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            backButton.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
            nextButton.setOnClickListener {
                val password = binding.newPasswordEditText.text.toString()
                val confirm = binding.confirmPasswordEditText.text.toString()
                val email = arguments?.getString("email")

                if (password.isEmpty()) {
                    binding.newPasswordEditText.error = "Please enter a password"
                    return@setOnClickListener
                }
                if (confirm.isEmpty()) {
                    binding.confirmPasswordEditText.error = "Please confirm the password"
                    return@setOnClickListener
                }
                if (password != confirm) {
                    binding.confirmPasswordEditText.error = "Passwords do not match"
                    return@setOnClickListener
                }
//                resetPassword(email, password)
                val builder = AlertDialog.Builder(requireContext())
                val dialogView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_password_reset_success, null)
                builder.setView(dialogView)
                val dialog = builder.create()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setCancelable(false)

                val continueButton = dialogView.findViewById<Button>(R.id.continueButton1)
                continueButton.setOnClickListener {
                    dialog.dismiss()
                    val signInFragment = SignInFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.container, signInFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                }

                dialog.show()
            }

            newPasswordVisibilityButton.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(
                    binding.newPasswordEditText,
                    binding.newPasswordVisibilityButton,
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