package com.example.mydiaryapp.welcome

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mydiaryapp.R
import com.example.mydiaryapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, OnBoardingOneFragment())
            .commit()
    }
}