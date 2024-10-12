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
    fun insertUser(user: User){
        viewModelScope.launch {
            val newUser=user
            newUser.hashPassword()
            withContext(Dispatchers.IO){
                try {
                    db.userDao().insertUser(newUser)
                    Log.d("insertUser","user insertado: $newUser")
                }catch (e: Exception){
                    Log.e("DatabaseError","Error al insertar usuario",e)
                }
            }
        }
    }
}