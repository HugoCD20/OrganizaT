package com.example.organizat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class login : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redireccion:TextView = view.findViewById(R.id.texto1)

        redireccion.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,registro())
                .addToBackStack(null)
                .commit()
        }
    }
}