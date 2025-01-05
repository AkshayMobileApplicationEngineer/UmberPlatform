package com.app.umberhouse.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.app.umberhouse.R
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    private lateinit var imgHome: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        imgHome = view.findViewById(R.id.imgHome)
        val imgUrl = "https://treecampus.ngo/wp-content/uploads/2024/11/Ngoslider-.jpeg"
        Glide.with(requireContext()).load(imgUrl).error(R.drawable.play_store_512).into(imgHome)

        return view
    }

}