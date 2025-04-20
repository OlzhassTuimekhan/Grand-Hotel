package kz.grand_hotel.ui.authorization

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kz.grand_hotel.R
import kz.grand_hotel.ui.authorization.login.SignInFragment


class AuthorizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        if (savedInstanceState == null) {
            showSignInFragment()
        }
    }

    private fun showSignInFragment() {
        val signInFragment: SignInFragment = SignInFragment()
        replaceFragment(signInFragment)
    }

    // Метод для замены фрагментов
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}