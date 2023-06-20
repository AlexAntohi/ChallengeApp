package com.example.challengeapp.data.tasks.challengeTasks

import android.os.AsyncTask
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.models.User

class InsertChallengeTask( private val userDatabase: UserDatabase?, private val listener: ChallengeRepository.OnSuccessListener) :
    AsyncTask<Challenge, Void, Void>() {

    override fun doInBackground(vararg challenges: Challenge?): Void? {
        userDatabase?.ChallengeDao()?.insert(challenges[0])
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        listener.onSuccess()
    }
}