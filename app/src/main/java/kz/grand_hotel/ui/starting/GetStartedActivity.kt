package kz.grand_hotel.ui.starting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import kz.grand_hotel.R

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_started)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_layout, NavHostFragment.create(R.navigation.nav_graph))
            .setPrimaryNavigationFragment(supportFragmentManager.findFragmentById(R.id.main_layout))
            .commit()
    }
}