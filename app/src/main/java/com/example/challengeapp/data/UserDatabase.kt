package com.example.challengeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challengeapp.data.dao.ChallengeDao
import com.example.challengeapp.data.dao.UserDao
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.models.User

@Database(entities = [User::class, Challenge::class], version = 2, exportSchema = true)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun ChallengeDao(): ChallengeDao

}