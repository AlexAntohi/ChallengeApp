package com.example.challengeapp.data.tasks.challengeTasks

import android.os.AsyncTask
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.models.Challenge

class GetChallengeByIdTask ( private val userDatabase: UserDatabase?, private val listener: ChallengeRepository.OnGetListener):
    AsyncTask<Int,Void,List<Challenge>>()
    {

        override fun doInBackground(vararg id: Int?): List<Challenge>? {
            return let{ id[0]?.let { it1 -> userDatabase?.challengeDao()?.getChallengeById(it1) } }
        }


        override fun onPostExecute(result: List<Challenge>) {
            super.onPostExecute(result)
            listener.onSuccess(result)
        }
    }