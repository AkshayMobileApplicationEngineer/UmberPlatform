package com.app.umberhouse.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.umberhouse.R
import com.app.umberhouse.activity.HomeActivity
import com.app.umberhouse.roomdatabase.DB.AppDB
import com.app.umberhouse.roomdatabase.Entity.UserTableEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object{
         const val DB_SHARE_PREF = "Data"
         const val USERNAME="username"
         const val PASSWORD="password"
         const val IsLoggedIn="isLoggedIn"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(DB_SHARE_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val usernameEditText = view.findViewById<EditText>(R.id.username)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()


            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else {
                isStoreinRoomDatabase(username, password)
                // Perform login logic here
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }

    private fun isStoreinRoomDatabase(username: String, password: String) {
        val login = UserTableEntity(username= username, password=password)


        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDB.getInstance(requireContext())
            database.ContactDao().insertCustomer(login)
            Log.e("isStoreinRoomDatabase", "isStoreinRoomDatabase")


            editor.putBoolean(IsLoggedIn, true)
            editor.putString(USERNAME, username)
            editor.putString(PASSWORD, password)
            editor.apply()
        }



    }


}
