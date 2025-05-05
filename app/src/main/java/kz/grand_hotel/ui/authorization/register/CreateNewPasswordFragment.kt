package kz.grand_hotel.ui.authorization.register

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentCreateNewPasswordBinding
import kz.grand_hotel.ui.GlobalData
import kz.grand_hotel.ui.authorization.login.SignInFragment
import kz.grand_hotel.utils.showLoading
import kz.grand_hotel.utils.hideLoading
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class CreateNewPasswordFragment : Fragment() {

    private var _binding: FragmentCreateNewPasswordBinding? = null
    private val binding get() = _binding!!

    private var isPasswordVisible = false
    private val client = OkHttpClient()


    private var email: String = ""
    private var resetToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString("email", "")
            resetToken = it.getString("reset_token", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            newPasswordVisibilityButton.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(
                    newPasswordEditText,
                    newPasswordVisibilityButton,
                    isPasswordVisible
                )
            }
            confirmPasswordVisibilityButton.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(
                    confirmPasswordEditText,
                    confirmPasswordVisibilityButton,
                    isPasswordVisible
                )
            }

            nextButton.setOnClickListener {

                errorTextView.visibility = View.GONE

                val newPass = newPasswordEditText.text.toString()
                val confPass = confirmPasswordEditText.text.toString()


                when {
                    newPass.isEmpty() -> {
                        newPasswordEditText.error = "Введите новый пароль"
                        return@setOnClickListener
                    }
                    confPass.isEmpty() -> {
                        confirmPasswordEditText.error = "Подтвердите пароль"
                        return@setOnClickListener
                    }
                    newPass != confPass -> {
                        confirmPasswordEditText.error = "Пароли не совпадают"
                        return@setOnClickListener
                    }
                }

                resetPassword(newPass, confPass)
            }
        }
    }

    private fun resetPassword(newPass: String, confPass: String) {
        showLoading()
        binding.errorTextView.visibility = View.GONE

        val url = "${GlobalData.ip}reset-password"
        val json = JSONObject().apply {
            put("email", email)
            put("reset_token", resetToken)
            put("new_password", newPass)
            put("new_password_confirmation", confPass)
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
                    Toast.makeText(requireContext(), "Сетевая ошибка", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val respString = response.body?.string().orEmpty()
                requireActivity().runOnUiThread {
                    hideLoading()
                    when (response.code) {
                        200 -> {

                            val builder = AlertDialog.Builder(requireContext())
                            val dialogView = layoutInflater.inflate(
                                R.layout.dialog_password_reset_success,
                                null
                            )
                            builder.setView(dialogView)
                            val dialog = builder.create().apply {
                                window?.setBackgroundDrawableResource(android.R.color.transparent)
                                setCancelable(false)
                            }
                            dialogView.findViewById<View>(R.id.continueButton1).setOnClickListener {
                                dialog.dismiss()

                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.container, SignInFragment())
                                    .commit()
                            }
                            dialog.show()
                        }
                        400, 404 -> {

                            val msg = JSONObject(respString)
                                .optString("message", "Ошибка. Попробуйте ещё раз")
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
