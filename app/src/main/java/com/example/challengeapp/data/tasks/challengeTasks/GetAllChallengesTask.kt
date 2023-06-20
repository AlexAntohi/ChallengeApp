package com.example.challengeapp.data.tasks.challengeTasks

import android.os.AsyncTask
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.models.Challenge

class GetAllChallengesTask (private val userDatabase: UserDatabase?, private val listener: ChallengeRepository.OnGetListener):
    AsyncTask<Void, Void, List<Challenge>>() {

    override fun doInBackground(vararg challenges: Void?): List<Challenge>? {
        return userDatabase?.challengeDao()?.getAllChallenges()
    }

    override fun onPostExecute(result: List<Challenge>) {
        super.onPostExecute(result)
        listener.onSuccess(result)
    }
}