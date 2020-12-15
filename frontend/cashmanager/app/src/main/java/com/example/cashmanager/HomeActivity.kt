package com.example.cashmanager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cashmanager.data.api.ApiResponse
import com.example.cashmanager.data.api.ApiService
import com.example.cashmanager.data.model.Product
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)

        PaymentConfiguration.init(
                applicationContext,
                "pk_test_51HpDk4BAlay8xPZcppOJ3QAtvMZhGnFddRAvSgza255sP6sUa27p6c1yDWgZHAuXnU5lHeRHIi8PEaa1IRCGRCuA00EDRLISY6"
        )
    }
}