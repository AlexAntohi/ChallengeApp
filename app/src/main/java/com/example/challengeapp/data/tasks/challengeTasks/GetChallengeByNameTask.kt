package com.example.challengeapp.data.tasks.challengeTasks

import android.os.AsyncTask
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.models.Challenge

class GetChallengeByNameTask(private val userDatabase: UserDatabase?, private val listener: ChallengeRepository.OnGetListener):
    AsyncTask<String, Void, List<Challenge>>() {

    override fun doInBackground(vararg name: String?): List<Challenge>? {
        return let{name[0]?.let{it1 -> userDatabase?.challengeDao()?.getChallengeByName(it1)
        }}
    }
    override fun onPostExecute(result: List<Challenge>) {
        super.onPostExecute(result)
        listener.onSuccess(result)
    }
}
