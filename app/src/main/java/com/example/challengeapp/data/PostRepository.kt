package com.example.challengeapp.data

import com.example.challengeapp.ApplicationController
import com.example.challengeapp.data.models.Post
import com.example.challengeapp.data.tasks.postTasks.DeletePostTask
import com.example.challengeapp.data.tasks.postTasks.GetAllPostsByUserTask
import com.example.challengeapp.data.tasks.postTasks.GetAllPostsTask
import com.example.challengeapp.data.tasks.postTasks.InsertPostTask

class PostRepository () {

    interface OnSuccessListener {
        fun onSuccess()
    }

    interface OnGetListener {
        fun onSuccess(items: List<Post>)
    }

    private var userDatabase: UserDatabase ?= null

    init {
        userDatabase = ApplicationController.getUserDatabase()
    }

    fun insertPost( newPost: Post, listener: OnSuccessListener) {

        InsertPostTask(userDatabase, listener).execute(newPost)

    }

    fun deletePost( myPost: Post, listener: OnSuccessListener) {

        DeletePostTask(userDatabase, listener).execute(myPost)

    }

    fun getAllPosts(listener: OnGetListener) {

        GetAllPostsTask(userDatabase, listener).execute()

    }
    fun getAllPostsByUsername(username:String,listener: OnGetListener){
        GetAllPostsByUserTask(userDatabase,listener).execute(username)
    }
}