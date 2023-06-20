package com.example.challengeapp.data.tasks.userTasks

import android.os.AsyncTask
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.User

class DeleteUsersTask (private val userDatabase: UserDatabase?, private val listener: UserRepository.OnSuccessListener) :
    AsyncTask<User,Void, Void>()
    {
        override fun doInBackground(vararg users: User?): Void? {
            userDatabase?.userDao()?.delete(users[0])
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            listener.onSuccess()
        }
}