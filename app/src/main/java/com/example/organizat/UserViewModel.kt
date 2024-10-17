package com.example.organizat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel: ViewModel() {
    private lateinit var db:appOrganizaT
    init {
        db= Room.databaseBuilder(
            MyApplication.getAppContext(),
            appOrganizaT::class.java,
            "organizatdb"
        ).build()
    }
    fun consultarUsuarios(onResult: (List<User>)->Unit){
            viewModelScope.launch {
                val users: List<User> = withContext(Dispatchers.IO){
                    val userDao=db.userDao()
                    userDao.getAll()
            }
                onResult(users)
        }
    }
    fun insertUser(user: User,onResult: (Boolean,String) -> Unit){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val existingEmailUser= db.userDao().getUserByEmail(user.email)
                    val existingUsername= db.userDao().getUserByUsername(user.username)

                    when{
                        existingEmailUser != null ->{
                            withContext(Dispatchers.Main){
                                onResult(false,"Correo ya registrado")
                            }
                        }
                        existingUsername != null ->{
                            withContext(Dispatchers.Main){
                                onResult(false,"Nombre de usuario ya registrado")
                            }
                        }
                        else ->{
                            user.hashPassword()
                            db.userDao().insertUser(user)
                            withContext(Dispatchers.Main){
                                onResult(true,"Usuario registrado con éxito")
                            }
                        }
                    }
                }catch (e: Exception){
                    Log.e("DatabaseError","Error al insertar usario",e)
                    withContext(Dispatchers.Main){
                        onResult(false,"Error al registrar usuario")
                    }
                }
            }
        }
    }
}