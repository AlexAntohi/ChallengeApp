package com.example.challengeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeapp.R
import com.example.challengeapp.adapters.PostsAdapter
import com.example.challengeapp.data.PostRepository
import com.example.challengeapp.data.models.Post
import org.w3c.dom.Text

class ProfileFragment : Fragment() {


    var username: String? = null
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
    }
    override fun onPause() {
        super.onPause()
        postsAdapter?.releaseAllPlayers()
    }
    private fun setupViews(view : View){
        val act = requireActivity()
        username = act.intent.getStringExtra("username")
        getPosts()
        setupRecyclerView(view)
        val usernameView = view.findViewById<TextView>(R.id.profile_username)
        usernameView.text = username
    }
    private fun setupRecyclerView(view : View){

        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerView_PostsProfile)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        postsAdapter = PostsAdapter(postsList)
        recyclerView.adapter = postsAdapter
    }
    private fun getPosts(){
        val onGetListener = object : PostRepository.OnGetListener {
            override fun onSuccess(items: List<Post>) {
                postsList.clear()
                items.forEach{post->
                    postsList.add(post)
                }
                postsAdapter?.notifyItemRangeChanged(0, postsList.size)
            }
        }
        if (username != null) {
            postRepository.getAllPostsByUsername(username!!,onGetListener)
        }
    }
}