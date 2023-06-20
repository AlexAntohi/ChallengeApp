package com.example.challengeapp.data

import com.example.challengeapp.ApplicationController
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.tasks.challengeTasks.GetAllChallengesTask
import com.example.challengeapp.data.tasks.challengeTasks.InsertChallengeTask


class ChallengeRepository() {

    interface OnSuccessListener {
        fun onSuccess()
    }

    interface OnGetListener {
        fun onSuccess(items: List<Challenge>)
    }

    private var userDatabase: UserDatabase ?= null

    init {
        userDatabase = ApplicationController.getUserDatabase()
    }

    fun insertChallenge( newChallenge: Challenge, listener: OnSuccessListener ){

        InsertChallengeTask(userDatabase, listener).execute(newChallenge)

    }

    fun getAllChallenges(listener: OnGetListener) {

        GetAllChallengesTask(userDatabase, listener).execute()

    }


}