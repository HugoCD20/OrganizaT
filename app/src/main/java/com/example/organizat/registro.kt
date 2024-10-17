package com.example.organizat

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels

class registro : Fragment(R.layout.fragment_registro) {
    private val UserViewModel: UserViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redireccion: ImageView = view.findViewById(R.id.boton2)

        val usernameEditText: EditText = view.findViewById(R.id.nameuser)
        val emailEditText: EditText = view.findViewById(R.id.email)
        val passwordEditText: EditText = view.findViewById(R.id.password)
        val confpasswordEditText: EditText = view.findViewById(R.id.confpassword)

        redireccion.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,login())
                .addToBackStack(null)
                .commit()
        }
        val redireccion2: Button = view.findViewById(R.id.boton3)

        redireccion2.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confpassword = confpasswordEditText.text.toString()

            if(validateInput(email,password,confpassword,username)){
                val user = User(username=username, email = email, password = password)
                UserViewModel.insertUser(user){ isSuccessful, message ->
                    Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                    if(isSuccessful){
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,login())
                            .addToBackStack(null)
                            .commit()
                    }

                }
            }
        }
    }
    private fun validateInput(email: String, password:String, confipassword:String, username: String): Boolean {
        if(email.isEmpty()){
            showToast("El Correo no puede estar vacío")
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showToast("El correo no tiene un formato válido")
            return false
        }
        if (password.length < 6){
            showToast("La contraseña debe tener al menos 6 caracteres")
            return false
        }
        if(password != confipassword){
            showToast("Las contraseñas no coinciden")
            return false
        }
        if (username.length < 4){
            showToast("El nombre de usuario debe tener al menos 4 caracteres")
            return false
        }
        return true
    }

    private fun showToast(message:String){
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

}