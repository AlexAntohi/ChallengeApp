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
import com.google.android.exoplayer2.upstream.DefaultDataSource.Factory


class PostsAdapter (val postsList: ArrayList<Post>) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {
    private val players: MutableList<ExoPlayer> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.post, parent, false)
        val postsViewHolder = PostsViewHolder(view)

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
    inner class PostsViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        private val username : TextView
        private val challengeName : TextView
        private val  video : StyledPlayerView //YoutubePlayerView
        var player: ExoPlayer? = ExoPlayer.Builder(itemView.context).build()
        init {
            username = view.findViewById<TextView>(R.id.text_view_username)
            challengeName = view.findViewById<TextView>(R.id.text_view_challengeName)
            video = view.findViewById<StyledPlayerView>(R.id.video_view_videoclip)

        }

        fun bind(post : Post){

            var myUser : User? = null
            val userRepository = UserRepository()

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
            val challengeRepository = ChallengeRepository()

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
            player?.setMediaSource(mediaSource)
            player?.prepare()
            player?.playWhenReady = false
            if(player!=null)
            {
                players.add(player!!)
            }

        }
        fun releasePlayer() {
            if (player != null) {
                player!!.release()
                player = null
            }
        }
        fun startPlayer() {
            player?.playWhenReady = true
        }

        fun pausePlayer() {
            player?.playWhenReady = false
        }
    }


}

