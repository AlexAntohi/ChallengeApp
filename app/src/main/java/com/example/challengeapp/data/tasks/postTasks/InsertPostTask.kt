package com.example.challengeapp.data.tasks.postTasks

import android.os.AsyncTask
import com.example.challengeapp.data.PostRepository
import com.example.challengeapp.data.UserDatabase
import com.example.challengeapp.data.models.Post

class InsertPostTask(private val userDatabase: UserDatabase?, private val listener: PostRepository.OnSuccessListener) :
    AsyncTask<Post, Void, Void>()  {

    override fun doInBackground(vararg posts: Post): Void? {
        userDatabase?.postDao()?.insert(posts[0])
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        listener.onSuccess()
    }
}