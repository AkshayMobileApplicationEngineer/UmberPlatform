package com.app.roomzy.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import android.widget.ViewFlipper

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.app.roomzy.MasterActivity
import com.app.roomzy.R
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException

class SignupFragment : Fragment() {

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var mobileField: EditText
    private lateinit var profileImage: ImageView
    private lateinit var roleField: Spinner

    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

    // Declare SharedPreferences and Editor as lateinit variables
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object {
        const val SHARE_PREF = "data"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_signup, container, false)

        // Initialize SharedPreferences and Editor
        sharedPref = requireActivity().getSharedPreferences(SHARE_PREF,Context.MODE_PRIVATE)
        editor = sharedPref.edit()

        // Initialize views
        viewFlipper = rootView.findViewById(R.id.viewFlipper)
        prevButton = rootView.findViewById(R.id.prevButton)
        nextButton = rootView.findViewById(R.id.nextButton)
        nameField = rootView.findViewById(R.id.nameField)
        emailField = rootView.findViewById(R.id.emailField)
        passwordField = rootView.findViewById(R.id.passwordField)
        mobileField = rootView.findViewById(R.id.mobileField)
        profileImage = rootView.findViewById(R.id.profileImage)
        roleField = rootView.findViewById(R.id.roleField)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
profileImage.setOnClickListener {
    chooseImage(it)
}


        // Set up Next and Previous buttons to navigate through ViewFlipper
        nextButton.setOnClickListener {
            if (viewFlipper.displayedChild < viewFlipper.childCount - 1) {
                viewFlipper.showNext()
            } else {
                // Handle Submit on the last screen
                if (isFormValid()) {
                    val name = nameField.text.toString()
                    val email = emailField.text.toString()
                    val password = passwordField.text.toString()
                    val mobile = mobileField.text.toString()
                    val role = roleField.selectedItem.toString()

                    // Show Toast with values
                    Toast.makeText(requireContext(),
                        "Name: $name, Email: $email, Password: $password, Mobile: $mobile, Role: $role",
                        Toast.LENGTH_SHORT).show()

                    // Log data using Log.e
                    Log.e("SignupFragment", "Name: $name, Email: $email, Password: $password, Mobile: $mobile, Role: $role")

                    // Save values in SharedPreferences
                    editor.putString("name", name)
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.putString("mobile", mobile)
                    editor.putString("role", role)
                    editor.apply()  // Asynchronously saves data

                    // Save data to Firebase based on role
                    saveDataToFirebase(name, email, password, mobile, role)

                    // Optionally, navigate to another screen (e.g., home or login)
                    val intent = Intent(requireContext(), MasterActivity::class.java)
                    intent.putExtra("key", "LoginFragment")
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            }
            updateButtonVisibility(viewFlipper)
        }

        prevButton.setOnClickListener {
            if (viewFlipper.displayedChild > 0) {
                viewFlipper.showPrevious()
            }
            updateButtonVisibility(viewFlipper)
        }

        // Set initial visibility for the buttons
        updateButtonVisibility(viewFlipper)
    }

    private fun updateButtonVisibility(viewFlipper: ViewFlipper) {
        prevButton.visibility =
            if (viewFlipper.displayedChild == 0) View.GONE else View.VISIBLE
        nextButton.text =
            if (viewFlipper.displayedChild == viewFlipper.childCount - 1) "Submit" else "Next"
    }

    private fun isFormValid(): Boolean {
        val name = nameField.text.toString()
        val email = emailField.text.toString()
        val password = passwordField.text.toString()
        val mobile = mobileField.text.toString()

        return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && mobile.isNotEmpty()
    }

    private fun saveDataToFirebase(name: String, email: String, password: String, mobile: String, role: String) {
        val userDatabase = FirebaseDatabase.getInstance().getReference(role)
        val userId = userDatabase.push().key ?: return

        val userData = mapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "mobile" to mobile
        )

        userDatabase.child(userId).setValue(userData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to save data", Toast.LENGTH_SHORT).show()
                }
            }
    }


    fun chooseImage(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            imageUri = data?.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                profileImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
