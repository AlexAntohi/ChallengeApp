package com.example.challengeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.challengeapp.data.UserRepository
import com.example.challengeapp.data.models.User

class MainActivity : AppCompatActivity() {
    private var signInButton: Button? = null
    private var loginButton: Button? = null
    private var editTextUsername: EditText? = null
    private var editTextPassword: EditText? = null
    private val userRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }
    private fun setupViews() {
        signInButton = findViewById(R.id.signInButton)
        loginButton= findViewById(R.id.loginButton)
        editTextUsername = findViewById(R.id.usernameEditText)
        editTextPassword = findViewById(R.id.passwordEditText)
        signInButton?.setOnClickListener {
            signIn()
        }
        loginButton?.setOnClickListener {
            login()
        }
    }
    private fun login()
    {
        val username = editTextUsername?.text?.toString()?.trim() ?: return
        when (username.isEmpty()) {
            true -> {
                editTextUsername?.error = getString(R.string.error_required)
                return
            }
            false -> editTextUsername?.error = null
        }
        val password = editTextPassword?.text?.toString()?.trim() ?: return
        when (password.isEmpty()) {
            true -> {
                editTextPassword?.error = getString(R.string.error_required)
                return
            }
            false -> editTextPassword?.error = null
        }
        val usernames: ArrayList<String> = ArrayList()
        val users: ArrayList<User> = ArrayList()

        val onGetListener = object : UserRepository.OnGetListener {
            override fun onSuccess(items: List<User>) {
                items.forEach{user->
                    usernames.add(user.username)
                }
                val onGetListenerLogin = object: UserRepository.OnGetListener{
                    override fun onSuccess(items: List<User>) {
                        items.forEach { user ->
                            users.add(user)
                        }

                        when (users.size == 0)
                        {
                            true -> {
                                editTextPassword?.error = getString(R.string.error_wrong_password)
                                return
                            }
                            false ->{
                                val intent = Intent(this@MainActivity, AppActivity::class.java)
                                intent.putExtra("username", username)
                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    this@MainActivity, "Success Login.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                when (usernames.size==0)
                {
                    true -> {
                        editTextUsername?.error = getString(R.string.error_does_not_exist)
                        return
                    }
                    false ->{editTextUsername?.error = null
                       userRepository.getUsersByUsernameAndPassword(username,password,onGetListenerLogin)
                    }
                }
            }
        }
        userRepository.getUsersById(username,onGetListener)
    }
    private fun signIn() {
        val username = editTextUsername?.text?.toString()?.trim() ?: return
        when (username.isEmpty()) {
            true -> {
                editTextUsername?.error = getString(R.string.error_required)
                return
            }
            false -> editTextUsername?.error = null
        }
        val password = editTextPassword?.text?.toString()?.trim() ?: return
        when (password.isEmpty()) {
            true -> {
                editTextPassword?.error = getString(R.string.error_required)
                return
            }
            false -> editTextPassword?.error = null
        }
        val usernames: ArrayList<String> = ArrayList()
        val onGetListener = object : UserRepository.OnGetListener {
            override fun onSuccess(items: List<User>) {
                items.forEach{user->
                    usernames.add(user.username)
                }
                when (usernames.size!=0)
                {
                    true -> {
                        editTextUsername?.error = getString(R.string.error_already_exists)
                        return
                    }
                    false ->{editTextUsername?.error = null
                        val user = User(
                            0,
                            username,
                            password
                        )
                        val onSuccessListener = object : UserRepository.OnSuccessListener {
                            override fun onSuccess() {
                                Toast.makeText(
                                    this@MainActivity, "Success.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        userRepository.insertUser(user,onSuccessListener)
                    }
                }
            }
        }
        userRepository.getUsersById(username,onGetListener)
    }
}