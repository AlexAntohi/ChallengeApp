package com.example.challengeapp.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.challengeapp.R
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.PostRepository
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.models.Post
import com.example.challengeapp.data.models.User
import kotlin.properties.Delegates

class AddPostFragment : Fragment() {

    private lateinit var mySelectedChallenge : String
    private lateinit var videoPath : String
    val SELECTED_Video_REQUEST_CODE = 1
    private var selectVideoButton : Button?= null
    private var savePostButton : Button?= null
    private val postsRepository = PostRepository()
    private val userRepository = UserRepository()
    private val challengeRepository = ChallengeRepository()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        selectVideoButton = view.findViewById(R.id.button_add_video)
        savePostButton = view.findViewById(R.id.button_savePost)

        beginSetup(view)

        selectVideoButton?.setOnClickListener{

            selectedVideoFromGallery()

        }

        savePostButton?.setOnClickListener{
            checkIfPostCanBeCreated()
        }

        super.onViewCreated(view, savedInstanceState)

    }

    private fun beginSetup(view : View){

    val myItems = listOf("Apa cu ulei", "Apa cu ulei la patrat")

    val autoCompleteTextView : AutoCompleteTextView = view.findViewById(R.id.autocomplete_textview)

    val myAdapter = ArrayAdapter(this.requireContext(), R.layout.list_challenge, myItems)

    autoCompleteTextView.setAdapter(myAdapter)

    autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener {
            adapterView, view, i, l ->
        val selectedItem = adapterView.getItemAtPosition(i)
        mySelectedChallenge = selectedItem.toString()

        Toast.makeText(this.requireContext(), "Item: $selectedItem ", Toast.LENGTH_SHORT).show()

    } }

    private fun selectedVideoFromGallery(){

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, SELECTED_Video_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SELECTED_Video_REQUEST_CODE && resultCode == RESULT_OK) {
            val videoUri = data?.data

            videoPath = videoUri.toString()
        }

    }

    private fun checkIfPostCanBeCreated() {

        if (videoPath == null || mySelectedChallenge == null) {
            Toast.makeText(
                this.requireContext(),
                "Please select both video and challenge.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val onSuccessListener = object : PostRepository.OnSuccessListener {
                override fun onSuccess() {
                    Log.e("post", "adaugat cu succes")
                }
            }
            val act = requireActivity()
            val username = act.intent.getStringExtra("username")
            val usernames: ArrayList<User> = ArrayList()
            val challengeNames: ArrayList<Challenge> = ArrayList()

            val onGetListener = object : UserRepository.OnGetListener {
                override fun onSuccess(items: List<User>) {
                    items.forEach { user ->
                        usernames.add(user)
                    }
                    Log.e("onGetListener", "m-am blocat " + usernames[0].userId.toString() )

                    val onnGetListener = object : ChallengeRepository.OnGetListener {
                        override fun onSuccess(items: List<Challenge>) {
                            items.forEach { challenge ->
                                challengeNames.add(challenge)
                            }
                            Log.e("onnGetListener","m-am blocat")
                            val post = Post (
                                0,
                                usernames[0].userId,
                                challengeNames[0].challengeId,
                                10,
                                videoPath

                            )
                            postsRepository.insertPost(post, onSuccessListener)
                        }
                    }
                    challengeRepository.getChallengeByName(mySelectedChallenge, onnGetListener)
                }
            }
            if (username != null) {
                userRepository.getUsersByUsername(username, onGetListener)

            }
        }
    }
}
