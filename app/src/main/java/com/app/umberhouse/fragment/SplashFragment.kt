package com.app.umberhouse.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.app.umberhouse.R
import com.app.umberhouse.activity.HomeActivity
import com.app.umberhouse.activity.MainActivity
import com.app.umberhouse.fragment.LoginFragment.Companion.DB_SHARE_PREF
import com.app.umberhouse.fragment.LoginFragment.Companion.IsLoggedIn

class SplashFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        sharedPreferences = requireActivity().getSharedPreferences(DB_SHARE_PREF, Context.MODE_PRIVATE)

        // Check network and execute the main function
        mainFunction()
        return view
    }

    // Function to check network connectivity
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    // Main function to perform actions based on network status
    private fun mainFunction() {
        if (isNetworkConnected()) {
            // Add a delay of 2 seconds before checking login status
            Handler().postDelayed({
                if (isAdded) {
                    checkLoginStatus() // Proceed to check login status after delay
                }
            }, 2000)
        } else {
            // Show an alert dialog when offline
            AlertDialog.Builder(requireContext())
                .setMessage("Please check your internet connection.")
                .setTitle("No Internet Connection")
                .setCancelable(false)
                .setPositiveButton("Retry") { _, _ ->
                    mainFunction() // Retry logic
                }
                .setNegativeButton("Exit") { _, _ ->
                    requireActivity().finish() // Exit the app
                }
                .show()
        }
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean(IsLoggedIn, false)

        if (isLoggedIn) {
            // Navigate to HomeActivity
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Close the current activity to avoid returning to splash
        } else {
            // Navigate to MainActivity with LoginFragment as the target
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                putExtra("key", "LoginFragment")
            }
            startActivity(intent)
            requireActivity().finish() // Close the current activity to avoid returning to splash
        }
    }
}
