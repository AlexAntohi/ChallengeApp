package com.example.challengeapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.challengeapp.R
import com.example.challengeapp.adapters.PostsAdapter
import com.example.challengeapp.data.PostRepository
import com.example.challengeapp.data.models.Post


class PostsFragment: Fragment() {


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
        setupViews(view)
        configSwipe(view)
        super.onViewCreated(view, savedInstanceState)
        }

    private fun configSwipe(view: View) {
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setColorSchemeResources(R.color.teal_700)
        swipe.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                swipe.isRefreshing=false
                setupViews(view)
            },1500)
        }
    }

    override fun onPause() {
        super.onPause()
        postsAdapter?.releaseAllPlayers()
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
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    autoPlayVisiblePlayers(recyclerView)
                } else {
                    pauseAllPlayers(recyclerView)
                }
            }
        })
    }
    private fun autoPlayVisiblePlayers(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition)
        if (viewHolder is PostsAdapter.PostsViewHolder) {
            viewHolder.startPlayer()

        }
    }
    private fun pauseAllPlayers(recyclerView: RecyclerView) {
        for (i in 0 until recyclerView.childCount) {
            val viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i))
            if (viewHolder is PostsAdapter.PostsViewHolder) {
                viewHolder.pausePlayer()
            }
        }
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
        postRepository.getAllPosts(onGetListener)
    }
    }
