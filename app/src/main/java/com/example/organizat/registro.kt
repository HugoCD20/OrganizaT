package com.example.organizat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels

class registro : Fragment(R.layout.fragment_registro) {
    private val UserViewModel: UserViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redireccion: ImageView = view.findViewById(R.id.boton2)

        redireccion.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,login())
                .addToBackStack(null)
                .commit()
        }
        val redireccion2: Button = view.findViewById(R.id.boton3)

        redireccion2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,login())
                .addToBackStack(null)
                .commit()

            UserViewModel.insertUser(User(username = "David", email="ejemplo@ejemplo.com", password = "123" ))
        }
    }

}