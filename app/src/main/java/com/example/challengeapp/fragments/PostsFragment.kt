package com.example.challengeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeapp.R
import com.example.challengeapp.adapters.ChallengeAdapter
import com.example.challengeapp.adapters.PostsAdapter
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.PostRepository
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.models.Post
import com.example.challengeapp.data.models.User

public class PostsFragment: Fragment() {


    private val postRepository = PostRepository()

    private val postsList by lazy {

        ArrayList<Post>()

    }

    private var postsAdapter: PostsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        }

    private fun setupViews(view : View){

        setupRecyclerView(view)
        getPosts()


    }

    private fun setupRecyclerView(view : View){

        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerView_Posts)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        postsAdapter = PostsAdapter(postsList)
        recyclerView.adapter = postsAdapter

    }

    private fun getPosts(){

        val onGetListener = object : PostRepository.OnGetListener {
            override fun onSuccess(items: List<Post>) {
                postsList.clear()
                items.forEach{challenge->
                    postsList.add(challenge)
                }
                postsAdapter?.notifyItemRangeChanged(0, postsList.size)

            }
        }
        postRepository.getAllPosts(onGetListener)

    }

    }
