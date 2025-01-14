package com.app.umberhouse.activity

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.app.umberhouse.R
import com.app.umberhouse.fragment.LoginFragment
import com.app.umberhouse.fragment.MoreFragment
import com.app.umberhouse.fragment.NotificationFragment
import com.app.umberhouse.fragment.SplashFragment
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //check debug mode
        val packageName = applicationContext.packageName
        val pm = applicationContext.packageManager
        val info = pm.getPackageInfo(packageName, 0)
        val versionName = info.versionName
        val isDebuggable =  (info.applicationInfo!!.flags and 2) != 0

        if(isDebuggable){
            //Toast.makeText(applicationContext,"Version: $versionName, Debug mode on",Toast.LENGTH_LONG).show()
        }

        //check if debug mode on the device
        //if (isDebuggable) return isDebuggable()

        val value = intent.getStringExtra("key")
        val fragment = when (value) {
            "MoreFragment" -> MoreFragment()
            "NotificationFragment" -> NotificationFragment()
            "LoginFragment"-> LoginFragment()
            else -> SplashFragment()
        }


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_main, fragment)
            .commit()
    }


    /**
     * isDebuggable function checks if the app is in developer mode.
     * If the app is in developer mode, a dialog is displayed to the user to disable developer mode.
     * If the app is not in developer mode, the app is closed.
     */

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun isDebuggable() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("You are in developer mode. Please disable developer mode.")
        builder.setPositiveButton("DISABLE") { _, _ ->
            // Navigate to Developer Options settings
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Handle case where Developer Options settings are not accessible
                Toast.makeText(this, "Developer options settings not found. Please disable manually.", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton("EXIT") { _, _ ->
            // Close the app
            finishAffinity()
        }
        builder.setCancelable(false) // Prevent dismissing the dialog by touching outside
        builder.create().show()
    }

}