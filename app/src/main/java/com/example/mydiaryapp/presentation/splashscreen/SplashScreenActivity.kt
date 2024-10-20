package com.example.mydiaryapp.presentation.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mydiaryapp.databinding.ActivitySplashScreenBinding
import com.example.mydiaryapp.presentation.dashboard.DashboardActivity
import com.example.mydiaryapp.presentation.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    private val splashScreenViewModel by viewModels<SplashScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            splashScreenViewModel.session.observe(this@SplashScreenActivity) {
                Log.d("value Id", "onCreate: $it")
                if (it != 0 && it != null) {
                    Log.d("berhasil masuk Id ", "Id =  $it")
                    startActivity(Intent(this@SplashScreenActivity, DashboardActivity::class.java))
                    finish()
                } else {
                    Log.d("berhasil masuk Id ", "Id= $it")
                    startActivity(Intent(this@SplashScreenActivity, WelcomeActivity::class.java))
                    finish()
                }
            }
        }
    }
}