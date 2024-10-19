package com.example.organizat

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import org.mindrot.jbcrypt.BCrypt


class login : Fragment(R.layout.fragment_login) {

    private val UserViewModel: UserViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redireccion:TextView = view.findViewById(R.id.texto1)

        redireccion.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,registro())
                .addToBackStack(null)
                .commit()
        }

        val email: EditText = view.findViewById(R.id.email2)
        val password: EditText = view.findViewById(R.id.Password2)
        val boton: Button = view.findViewById(R.id.boton_iniciar_sesion)

        boton.setOnClickListener {
            val email2 = email.text.toString()
            val password2 = password.text.toString()

            if(email2.isNotEmpty() && password2.isNotEmpty()){
                loginUser(email2,password2)
            }else{
                Toast.makeText(requireContext(),"Completa campos",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun restartActivity(){
        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }
    private fun loginUser(email: String, password:String){
        UserViewModel.consultarUsuarioPorEmail(email){user->
            if(user !=null && BCrypt.checkpw(password,user.password)){
                Log.d("LoginActivity","Login exitoso")

                val sharedPreferences = requireActivity().getSharedPreferences("user_prefs",0)
                with(sharedPreferences.edit()){
                    putString("user_email",email)
                    apply()
                }
                (activity as? MainActivity)?.updateMenuTitle("Logout")
                restartActivity()
            }else{
                Log.w("LoginActivity","Error de autenticacion")
                Toast.makeText(requireContext(),"Email o contraseña incorrectos",Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun goToMainActivity(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,Inicio2())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs",0)
        val userEmail = sharedPreferences.getString("user_email",null)

        if(userEmail != null){
            Log.d("LoginActivity","sesión ya iniciada con : $userEmail")
            goToMainActivity()
        }
    }

}