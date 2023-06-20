package com.example.challengeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challengeapp.data.dao.ChallengeDao
import com.example.challengeapp.data.dao.PostDao
import com.example.challengeapp.data.dao.UserDao
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.models.Post
import com.example.challengeapp.data.models.User

@Database(entities = [User::class, Challenge::class, Post::class], version = 3, exportSchema = true)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun challengeDao(): ChallengeDao

    abstract fun postDao(): PostDao

}