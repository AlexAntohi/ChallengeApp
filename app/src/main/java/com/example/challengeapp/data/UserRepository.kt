package com.example.challengeapp.data

import com.example.challengeapp.ApplicationController
import com.example.challengeapp.data.models.User
import com.example.challengeapp.data.tasks.userTasks.DeleteUsersTask
import com.example.challengeapp.data.tasks.userTasks.GetAllUsersTask
import com.example.challengeapp.data.tasks.userTasks.GetByUsernameAndPasswordTask
import com.example.challengeapp.data.tasks.userTasks.GetByUsernameTask
import com.example.challengeapp.data.tasks.userTasks.InsertUsersTask

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
    fun getUsersById(username:String,listener: OnGetListener)
    {
        GetByUsernameTask(userDatabase,listener).execute(username)
    }
    fun getUsersByUsernameAndPassword(username: String,password: String,listener: OnGetListener)
    {
        GetByUsernameAndPasswordTask(userDatabase,listener).execute(Pair(username,password))
    }

}