package com.example.organizat

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mindrot.jbcrypt.BCrypt

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
            val username: String,
            val email: String,
            var password: String,
){
    fun hashPassword(){
        this.password=BCrypt.hashpw(password,BCrypt.gensalt())
    }
}