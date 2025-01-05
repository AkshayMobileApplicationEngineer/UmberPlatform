package com.app.umberhouse.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.app.umberhouse.R
import com.app.umberhouse.fragment.HomeFragment
import com.app.umberhouse.fragment.LoginFragment.Companion.DB_SHARE_PREF
import com.app.umberhouse.fragment.MoreFragment
import com.app.umberhouse.roomdatabase.DB.AppDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.statusBarColor = ContextCompat.getColor(this, R.color.vivid_sky_blue)
        initializeUI()
        initializeClicks()
        // Set default fragment
        loadFragment(HomeFragment())

        // Set toolbar title
        toolbar.title = "Title"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbar.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.vivid_sky_blue))

        // Open drawer when toolbar is clicked
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

       toolbar.setOnMenuItemClickListener {
           when(it.itemId){
               R.id.home->{
                   toolbar.title = "Home"
                   loadFragment(HomeFragment())
                   drawerLayout.closeDrawer(GravityCompat.START)
                   true
               }

               else -> false
           }
       }
        // Handle menu item clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    toolbar.title = "Home"
                    loadFragment(HomeFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.more -> {
                    toolbar.title = "More"
                    loadFragment(MoreFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.logout -> {
                    logout()

                    true
                }

                else -> false
            }
        }

        // Handle clicks on the header view
        val headerView = navigationView.getHeaderView(0)

        // Click listener for the avatar
        val avatar: ImageView = headerView.findViewById(R.id.nav_header_avatar)
        avatar.setOnClickListener {
            Toast.makeText(this, "Avatar clicked", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Click listener for the name
        val name: TextView = headerView.findViewById(R.id.nav_header_name)
        name.setOnClickListener {
            Toast.makeText(this, "Name clicked", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Click listener for the email
        val email: TextView = headerView.findViewById(R.id.nav_header_email)
        email.setOnClickListener {
            Toast.makeText(this, "Email clicked", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        if (savedInstanceState == null) {
            toolbar.title= "Home"
            loadFragment(HomeFragment())
        }
    }

    private fun logout() {
       clearSharedPreferences()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("key", "LoginFragment")
        startActivity(intent)
    }


    // Function to clear Room database
    private fun clearRoomDatabase() {
        // Use coroutine to run Room database operations on a background thread
        lifecycleScope.launch {
            val database = AppDB.getInstance(this@HomeActivity)
            val dao = database.ContactDao() // Replace with your actual DAO
        }
    }

    // Function to clear SharedPreferences
    private fun clearSharedPreferences() {
        val sharedPreferences = this@HomeActivity.getSharedPreferences(DB_SHARE_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Clears all saved preferences
        editor.apply()
    }

    private fun initializeUI() {
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigation_view)

    }

    private fun initializeClicks() {
        
    }

    // Function to load fragments
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }


}
