package kz.grand_hotel.ui.authorization.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentOtpResetBinding
import kz.grand_hotel.databinding.FragmentOtpVerificationBinding
import kz.grand_hotel.databinding.FragmentSignUpBinding


class OtpVerificationFragment : Fragment() {

    private var _binding: FragmentOtpVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOtpVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        with(binding) {

            continueButton.setOnClickListener {
                val digit1 = binding.otpDigit1TextView.text.toString().trim()
                val digit2 = binding.otpDigit2TextView.text.toString().trim()
                val digit3 = binding.otpDigit3TextView.text.toString().trim()
                val digit4 = binding.otpDigit4TextView.text.toString().trim()

                if (digit1.isEmpty() || digit2.isEmpty() || digit3.isEmpty() || digit4.isEmpty()) {
                    Toast.makeText(requireContext(), "Введите полный код", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val enteredCode = "$digit1$digit2$digit3$digit4"

                val bundle = Bundle()
                bundle.putString("enteredCode", enteredCode)
                val CreateNewPasswordFragment = CreateNewPasswordFragment()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, CreateNewPasswordFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }


            otpDigit1TextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        otpDigit2TextView.requestFocus()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            otpDigit2TextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        otpDigit3TextView.requestFocus()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            otpDigit3TextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        otpDigit4TextView.requestFocus()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            backButton.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
            resendCodeTextView.setOnClickListener {

            }
        }
    }


}