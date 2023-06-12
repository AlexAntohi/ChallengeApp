package com.example.challengeapp.data.tasks

import android.os.AsyncTask
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.User

class GetAllUsersTask (private val userDatabase: UserDatabase?, private val listener: UserRepository.OnGetListener):
    AsyncTask<Void, Void, List<User>>() {

    override fun doInBackground(vararg p0: Void?): List<User>? {
        return userDatabase?.userDao()?.getAll()
    }

    override fun onPostExecute(result: List<User>) {
        super.onPostExecute(result)
        listener.onSuccess(result)
    }

    }