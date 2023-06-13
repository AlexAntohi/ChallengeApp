package com.example.challengeapp.data.tasks

import android.os.AsyncTask
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.User

class GetByUsernameAndPasswordTask (private val userDatabase: UserDatabase?, private val listener: UserRepository.OnGetListener):
    AsyncTask<Pair<String,String>,Void , List<User>>() {

    override fun doInBackground(vararg params: Pair<String, String>?): List<User>? {
        val username = params[0]?.first
        val password = params[0]?.second
        return username?.let { password?.let { userDatabase?.userDao()?.getAllByUsernameAndPassword(username, password) } }
    }
    override fun onPostExecute(result: List<User>) {
        super.onPostExecute(result)
        listener.onSuccess(result)
    }
}
