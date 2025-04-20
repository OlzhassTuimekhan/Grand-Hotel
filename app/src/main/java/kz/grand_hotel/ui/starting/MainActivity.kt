package kz.grand_hotel.ui.starting

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

import kz.grand_hotel.R

//Hello
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            val getStartedActivityIntent = Intent(this, GetStartedActivity::class.java)
            startActivity(getStartedActivityIntent)

            finish()
        }, 2345)
    }
}

