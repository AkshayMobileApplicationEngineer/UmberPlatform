package com.app.roomzy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.app.roomzy.fragment.LoginFragment
import com.app.roomzy.fragment.SignupFragment
import com.app.roomzy.fragment.SplashFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // Full-Screen Mode
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        // Hide the ActionBar
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        // Set status bar color

        // Disable Night Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Handle navigation based on the "key" passed via intent
        val fragmentKey = intent.getStringExtra("key") ?: "SplashFragment"
        val fragment = when (fragmentKey) {

            "LoginFragment" -> LoginFragment()
            "SignupFragment"->SignupFragment()
            "SplashFragment" -> SplashFragment()

            else -> SplashFragment()
        }

        // Clear previous screen and load the selected fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitAllowingStateLoss() // Ensures immediate execution
    }
}
