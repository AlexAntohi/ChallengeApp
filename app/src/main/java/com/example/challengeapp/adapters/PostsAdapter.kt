package com.example.challengeapp.adapters

import com.google.android.youtube.player.YouTubePlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeapp.R
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.models.Post
import com.example.challengeapp.data.models.User
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayerView


class PostsAdapter (val postsList: ArrayList<Post>) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.post, parent, false)
        val postsViewHolder = PostsViewHolder(view, parent)

        return postsViewHolder
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post : Post = postsList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return this.postsList.size
    }

    inner class PostsViewHolder(view: View, myParent: ViewGroup): RecyclerView.ViewHolder(view)
    {

        private val username : TextView
        private val challengeName : TextView
        private val video : VideoView //YoutubePlayerView
        private val myContext = myParent.context


        init {
            username = view.findViewById<TextView>(R.id.text_view_username)
            challengeName = view.findViewById<TextView>(R.id.text_view_challengeName)
            video = view.findViewById<VideoView>(R.id.video_view_videoclip)
        }

        fun bind(post : Post){

            var myUser : User? = null
            var userRepository = UserRepository()

            val onGetListener = object : UserRepository.OnGetListener {
                override fun onSuccess(items: List<User>) {
                    items.forEach{user->
                        myUser = user
                    }
                    username.text = myUser?.username
                }
            }
            userRepository.getUsersById(post.userId ,onGetListener)

            var myChallenge : Challenge? = null
            var challengeRepository = ChallengeRepository()

            val onnGetListener = object: ChallengeRepository.OnGetListener {

                override fun onSuccess(items: List<Challenge>) {
                    items.forEach{challenge ->
                        myChallenge = challenge
                    }
                    challengeName.text = myChallenge?.name
                }
            }
            challengeRepository.getChallengeById(post.challengeId, onnGetListener)

            val uri: Uri = Uri.parse(post.videoUrl)

                video.setVideoURI(uri)

            val mediaController = MediaController(myContext)

            mediaController.setAnchorView(video)
            //mediaController.setMediaPlayer(video)

            video.setMediaController(mediaController)

            //video.setVideoPath(post.videoUrl)
            video.setVideoURI(Uri.parse(post.videoUrl))
            video.requestFocus()
            video.start();

//            val listener = object : YouTubePlayer.OnInitializedListener {
//                override fun onInitializationSuccess(
//                    p0: YouTubePlayer.Provider?,
//                    p1: YouTubePlayer?,
//                    p2: Boolean
//                ) {
//                   TODO("video.loadVideo not working")
//                }
//
//                override fun onInitializationFailure(
//                    p0: YouTubePlayer.Provider?,
//                    p1: YouTubeInitializationResult?
//                ) {
//                    TODO("Not yet implemented")
//                }
//            }

        }

    }

}