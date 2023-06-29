package com.example.challengeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.challengeapp.data.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>?

    @Query("SELECT * FROM user WHERE username = :username")
    fun getAllByUsername(username: String): List<User>?

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserById(userId: Int): List<User>?

    @Query("SELECT * FROM user WHERE username = :username AND password = :password ")
    fun getAllByUsernameAndPassword(username: String,password: String): List<User>?

    @Insert
    fun insert(user: User?)

    @Delete
    fun delete(user: User?)



}