package com.example.challengeapp

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
        loginButton=findViewById(R.id.loginButton)
        editTextUsername = findViewById(R.id.usernameEditText)
        editTextPassword = findViewById(R.id.passwordEditText)
        signInButton?.setOnClickListener {
            signIn()
        }
    }
    private fun signIn() {
        val username = editTextUsername?.text?.toString() ?: return
        when (username.isEmpty()) {
            true -> {
                editTextUsername?.error = getString(R.string.error_required)
                return
            }
            false -> editTextUsername?.error = null
        }
        val password = editTextPassword?.text?.toString() ?: return
        when (password.isEmpty()) {
            true -> {
                editTextPassword?.error = getString(R.string.error_required)
                return
            }
            false -> editTextPassword?.error = null
        }

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