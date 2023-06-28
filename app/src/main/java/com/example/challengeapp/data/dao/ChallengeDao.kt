package com.example.challengeapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.challengeapp.data.models.Challenge

@Dao
interface ChallengeDao {

    @Insert
    fun insert(challenge: Challenge?)

    @Query("SELECT * FROM challenge")
    fun getAllChallenges(): List<Challenge>?

    @Query("SELECT * FROM challenge WHERE challengeId = :challengeId")
    fun getChallengeById(challengeId: Int): List<Challenge>?

}