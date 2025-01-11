package com.app.roomzy

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.app.roomzy.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MasterActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        // Set status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)

        // Initialize Toolbar
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        toolbar.title = "Roomzy"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            debugToast("Drawer opened")
        }

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    debugToast("Home clicked")
                    loadFragment(HomeFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.notifications -> {
                    debugToast("Notifications clicked")
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

        // Handle Logout from Toolbar Menu
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    debugToast("Logout clicked")
                    logout()
                    true
                }
                else -> false
            }
        }

        // Handle clicks on the header view of Navigation Drawer
        val headerView = findViewById<NavigationView>(R.id.navigation_view).getHeaderView(0)

        // Click listener for the avatar
        val avatar: ImageView = headerView.findViewById(R.id.profileImage)
        avatar.setOnClickListener {
            debugToast("Avatar clicked")
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Click listener for the name
        val name: TextView = headerView.findViewById(R.id.name)
        name.setOnClickListener {
            debugToast("Name clicked")
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Click listener for the email
        val email: TextView = headerView.findViewById(R.id.email)
        email.setOnClickListener {
            debugToast("Email clicked")
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Click listener for theme icon
        val theme: ImageView = headerView.findViewById(R.id.theme)
        theme.setOnClickListener {
            debugToast("Theme clicked")
        }

        // Click listener for the dropdown icon
        val dropdownIcon: ImageView = headerView.findViewById(R.id.dropdownIcon)
        dropdownIcon.setOnClickListener {
            debugToast("Dropdown clicked")
        }

        // Initialize BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    debugToast("Home selected")
                    loadFragment(HomeFragment())
                    toolbar.title = "Home"
                    true
                }
                R.id.nav_course -> {
                    debugToast("Course selected")
                    toolbar.title = "Course"
                    true
                }
                R.id.nav_profile -> {
                    debugToast("Profile selected")
                    toolbar.title = "Profile"
                    true
                }
                R.id.nav_game -> {
                    debugToast("Game selected")
                    toolbar.title = "Game"
                    true
                }
                else -> false
            }
        }

        // Load default fragment if no saved instance
        if (savedInstanceState == null) {
            toolbar.title = "Home"
            bottomNavigationView.selectedItemId = R.id.nav_home
            debugToast("Default fragment loaded: Home")
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragment_master, fragment)
            .commit()
        debugToast("Fragment loaded: ${fragment.javaClass.simpleName}")
    }

    private fun logout() {
        debugToast("Logging out and navigating to MainActivity")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("key", "LoginFragment")
        startActivity(intent)
        finish()
    }

    private fun debugToast(message: String) {
        Toast.makeText(this, "DEBUG: $message", Toast.LENGTH_SHORT).show()
    }
}
