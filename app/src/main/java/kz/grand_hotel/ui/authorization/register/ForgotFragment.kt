package kz.grand_hotel.ui.authorization.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.grand_hotel.R
import kz.grand_hotel.databinding.FragmentForgotBinding
import kz.grand_hotel.databinding.FragmentSignInBinding


class ForgotFragment : Fragment() {

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            backButton.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }

            nextButton.setOnClickListener {
                val email = binding.emailEditText.text.toString().trim()
                if (email.isEmpty()) {
                    binding.emailEditText.error = "Email is required"
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailEditText.error = "Invalid email address"
                } else {
                    val bundle = Bundle()
                    bundle.putString("email", email)
                    val OtpResetFragment = OtpResetFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.container, OtpResetFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                }

            }

        }
    }


}