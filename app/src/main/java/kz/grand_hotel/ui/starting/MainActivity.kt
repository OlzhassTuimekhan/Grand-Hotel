package kz.grand_hotel.ui.starting

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity

import kz.grand_hotel.R
import kz.grand_hotel.ui.GlobalData
import kz.grand_hotel.ui.authorization.AuthorizationActivity
import kz.grand_hotel.ui.menu.MenuActivity

//Hello
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)

            if (token.isNullOrEmpty()) {
                val intent = Intent(this, AuthorizationActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }

            finish()
        }, 2345)
    }
}

