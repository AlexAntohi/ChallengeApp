package com.example.challengeapp.data.tasks.userTasks

import android.os.AsyncTask
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.User

class GetUserByIdTask (private val userDatabase: UserDatabase?, private val listener: UserRepository.OnGetListener):
    AsyncTask<Int, Void, List<User>>() {

    override fun doInBackground(vararg id: Int?): List<User>? {
        return let{ id[0]?.let { it1 -> userDatabase?.userDao()?.getUserById(it1) } }
    }

    override fun onPostExecute(result: List<User>) {
        super.onPostExecute(result)
        listener.onSuccess(result)
    }

}