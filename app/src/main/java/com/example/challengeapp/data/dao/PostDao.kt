package com.example.challengeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.challengeapp.data.models.Post

@Dao
interface PostDao {

    @Insert
    fun insert(post: Post?)

    @Query("SELECT * FROM post")
    fun getAllPosts() : List<Post>?

    @Delete
    fun delete(post: Post?)
}