package com.example.challengeapp.data.tasks.postTasks

import android.os.AsyncTask
import com.example.challengeapp.data.PostRepository
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.models.Post

class GetAllPostsTask(private val userDatabase: UserDatabase?, private val listener: PostRepository.OnGetListener):
    AsyncTask<Void, Void, List<Post>>() {

    override fun doInBackground(vararg posts: Void?): List<Post>? {
        return userDatabase?.postDao()?.getAllPosts()
    }

    override fun onPostExecute(result: List<Post>) {
        super.onPostExecute(result)
        listener.onSuccess(result)
    }
}