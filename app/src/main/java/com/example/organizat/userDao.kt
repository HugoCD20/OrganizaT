package com.example.organizat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface userDao {

    @Query("SELECT * FROM User")
    suspend fun getAll():List<User>

    @Query("SELECT * FROM User where email= :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM User where username= :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun delete(user:User)
}