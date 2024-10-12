package com.example.organizat

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class appOrganizaT : RoomDatabase() {
     abstract fun userDao(): userDao
}