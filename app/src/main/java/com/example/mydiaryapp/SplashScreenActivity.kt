package com.example.mydiaryapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mydiaryapp.databinding.ActivitySplashScreenBinding
import com.example.mydiaryapp.welcome.WelcomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        CoroutineScope(Dispatchers.Main).launch{
            delay(3000L)

            //TODO check state. if user login = true , UI must to change to main activity
            //TODO else , UI must change to welcome activity
            startActivity(Intent(this@SplashScreenActivity, WelcomeActivity::class.java))
            finish()
        }
    }
}