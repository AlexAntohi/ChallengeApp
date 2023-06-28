package com.example.challengeapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeapp.R
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.models.Post
import com.example.challengeapp.data.models.User
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource.Factory


class PostsAdapter (val postsList: ArrayList<Post>) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {
    private val players: MutableList<ExoPlayer> = mutableListOf()
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
    override fun onViewRecycled(holder: PostsViewHolder) {
        super.onViewRecycled(holder)
        holder.releasePlayer()
    }
    override fun getItemCount(): Int {
        return this.postsList.size
    }
    fun releaseAllPlayers() {
        for (player in players) {
            player.release()
        }
        players.clear()
    }
//    fun initializePlayers() {
//        for (player in players) {
//            // Add any necessary setup for resuming playback, such as setting up media sources and resuming playback position.
//            // You can use the same logic you used in the `bindData()` method of the ViewHolder.
//
//            // Example:
//            player.playWhenReady = true // Resume playback
//        }
//    }
    inner class PostsViewHolder(view: View, myParent: ViewGroup): RecyclerView.ViewHolder(view)
    {

        var player: ExoPlayer? = null

        private val username : TextView
        private val challengeName : TextView
        private val  video : StyledPlayerView //YoutubePlayerView
        private val myContext = myParent.context


        init {
            username = view.findViewById<TextView>(R.id.text_view_username)
            challengeName = view.findViewById<TextView>(R.id.text_view_challengeName)
            video = view.findViewById<StyledPlayerView>(R.id.video_view_videoclip)
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


            player = ExoPlayer.Builder(itemView.context).build()
            video.player = player
            val videoUri = Uri.parse(post.videoUrl)
            val mediaItem: MediaItem = MediaItem.fromUri(videoUri)
            val mediaSource: MediaSource =
                ProgressiveMediaSource.Factory(Factory(itemView.context))
                    .createMediaSource(mediaItem)
            player!!.setMediaSource(mediaSource)
            player!!.prepare()
            player!!.playWhenReady = false
            players.add(player!!)


//            val videoUri = Uri.parse(post.videoUrl)
//            //video.setVideoPath(post.videoUrl)
//            video.setVideoURI(videoUri)
//            video.requestFocus()
//            video.start()
//
//
//            val mediaController = MediaController(myContext)
//
//            mediaController.setAnchorView(video)
//
//            video.setMediaController(mediaController)

    /*        val uri: Uri = Uri.parse(post.videoUrl)

                video.setVideoURI(uri)

            val mediaController = MediaController(myContext)

            mediaController.setAnchorView(video)
            //mediaController.setMediaPlayer(video)

            video.setMediaController(mediaController)

            //video.setVideoPath(post.videoUrl)
            video.setVideoURI(Uri.parse(post.videoUrl))
            video.requestFocus()
            video.start();*/

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
        fun releasePlayer() {
            if (player != null) {
                player!!.release()
                player = null
            }
        }
    }


}

