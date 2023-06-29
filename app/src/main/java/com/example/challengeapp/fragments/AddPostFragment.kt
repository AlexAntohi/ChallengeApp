package com.example.challengeapp.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.challengeapp.R
import com.example.challengeapp.data.ChallengeRepository
import com.example.challengeapp.data.PostRepository
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.Challenge
import com.example.challengeapp.data.models.Post
import com.example.challengeapp.data.models.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class AddPostFragment : Fragment() {

    private lateinit var mySelectedChallenge : String
    private lateinit var videoPath : String
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
        savePostButton!!.isEnabled=false
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
       val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type="video/*"
        videoLauncher.launch(intent)
    }
    val videoLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK)
        {
            if(it.data!=null){
                val ref = Firebase.storage.reference.child("Video/"+System.currentTimeMillis())
                ref.putFile(it.data!!.data!!).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener{
                        Firebase.database.reference.child("Video").push().setValue(it.toString())
                        Toast.makeText(this.requireContext(),"Uploaded video",Toast.LENGTH_SHORT).show()
                        videoPath= it.toString()
                        savePostButton!!.isEnabled=true
                    }
                }

            }
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
