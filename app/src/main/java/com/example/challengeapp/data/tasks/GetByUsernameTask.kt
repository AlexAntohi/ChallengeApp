package com.example.challengeapp.data.tasks

import android.os.AsyncTask
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.User

class GetByUsernameTask (private val userDatabase: UserDatabase?, private val listener: UserRepository.OnGetListener):
    AsyncTask<String, Void, List<User>>() {

    override fun doInBackground(vararg users: String?): List<User>? {
        return users[0]?.let { userDatabase?.userDao()?.getAllByUsername(it) }
    }

    override fun onPostExecute(result: List<User>) {
        super.onPostExecute(result)
        listener.onSuccess(result)
    }

}