package com.example.challengeapp.data

import com.example.challengeapp.ApplicationController
import com.example.challengeapp.data.models.User
import com.example.challengeapp.data.tasks.userTasks.*

class UserRepository () {

    interface OnSuccessListener {
        fun onSuccess()
    }

    interface OnGetListener {
        fun onSuccess(items: List<User>)
    }
    private var userDatabase: UserDatabase ?= null

    init {
        userDatabase = ApplicationController.getUserDatabase()
    }

    fun insertUser( newUser: User, listener: OnSuccessListener ){

        InsertUsersTask(userDatabase, listener).execute(newUser)
    }

    fun deleteUser( myUser: User, listener: OnSuccessListener ){

        DeleteUsersTask(userDatabase, listener).execute(myUser)

    }

    fun getAllUsers(listener: OnGetListener){

        GetAllUsersTask(userDatabase, listener).execute()
    }
    fun getUsersByUsername(username:String, listener: OnGetListener)
    {
        GetByUsernameTask(userDatabase,listener).execute(username)
    }
    fun getUsersByUsernameAndPassword(username: String,password: String,listener: OnGetListener)
    {
        GetByUsernameAndPasswordTask(userDatabase,listener).execute(Pair(username,password))
    }

    fun getUsersById(userId: Int, listener: OnGetListener)
    {
        GetUserByIdTask(userDatabase,listener).execute(userId)
    }

}