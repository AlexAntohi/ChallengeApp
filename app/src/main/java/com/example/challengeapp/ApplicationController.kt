package com.example.challengeapp

import android.app.Application
import android.service.autofill.UserData
import androidx.room.Room
import com.example.challengeapp.data.UserDatabase

class ApplicationController : Application() {

    companion object {

        private var instance : ApplicationController ?= null
        private var userDatabase : UserDatabase ?= null

        fun getUserDatabase(): UserDatabase? {
            return userDatabase
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupDatabase()
    }

    private fun setupDatabase(){
        val databaseName = "UserDb"
        userDatabase = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            databaseName
        ).build()
    }

}