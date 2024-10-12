package com.example.organizat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class inicio : Fragment(R.layout.fragment_inicio) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val redireccion: TextView = view.findViewById(R.id.boton1)

        redireccion.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,login())
                .addToBackStack(null)
                .commit()
        }
    }

}