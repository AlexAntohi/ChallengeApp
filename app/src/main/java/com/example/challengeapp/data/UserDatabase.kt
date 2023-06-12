package com.example.challengeapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.challengeapp.data.dao.UserDao
import com.example.challengeapp.data.models.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

}