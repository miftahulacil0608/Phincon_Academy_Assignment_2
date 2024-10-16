package com.example.mydiaryapp.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.ActivityAuthenticationBinding
import com.example.mydiaryapp.welcome.WelcomeActivity

class AuthenticationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAuthenticationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //TODO inflate fragment in here
        //TODO Default fragment register

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_authentication, RegisterFragment())
            .commit()
    }

}