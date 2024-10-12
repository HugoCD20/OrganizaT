package com.example.organizat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface userDao {

    @Query("SELECT * FROM User")
    suspend fun getAll():List<User>

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun delete(user:User)
}