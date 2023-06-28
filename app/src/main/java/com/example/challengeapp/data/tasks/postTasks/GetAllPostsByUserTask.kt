package com.example.challengeapp.data.tasks.postTasks

import android.os.AsyncTask
import com.example.challengeapp.data.PostRepository
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.models.Post

class GetAllPostsByUserTask(private val userDatabase: UserDatabase?, private val listener: PostRepository.OnGetListener):
    AsyncTask<String, Void, List<Post>>() {

    override fun doInBackground(vararg username: String?): List<Post>? {
        return userDatabase?.postDao()?.getPostsByUsername(username[0])
    }

    override fun onPostExecute(result: List<Post>) {
        super.onPostExecute(result)
        listener.onSuccess(result)
    }
}